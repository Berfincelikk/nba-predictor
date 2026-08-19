import os
import time
import joblib
import pandas as pd
import requests  
from typing import Optional, List
from datetime import datetime, timedelta

from fastapi import FastAPI, HTTPException, Query
from pydantic import BaseModel

# nba_api 
from nba_api.stats.endpoints import teamgamelog, teamdashboardbygeneralsplits
from nba_api.stats.static import teams

app = FastAPI(title="Sade NBA Tahmin API - ESPN Destekli")

#Model yükleme
try:
    model = joblib.load("nba_xgboost_super_model.pkl")
    print("✅ Model başarıyla yüklendi.")
except Exception as e:
    print(f"⚠️ Model bulunamadı. Hata: {e}")
    model = None


# ŞEMALAR VE YARDIMCI FONKSİYONLAR

class GamePreviewResponse(BaseModel):
    gameId: str
    homeTeam: str
    awayTeam: str
    matchDate: str
    matchTime: str
    arena: str

class GameDetailResponse(BaseModel):
    homeTeam: str
    awayTeam: str
    matchDate: str
    matchTime: str
    winner: str
    homeProbability: int
    awayProbability: int
    arena: str
    city: str
    homeForm: str
    awayForm: str

def get_nba_api_team_id(team_name: str) -> int:
    """ESPN'den gelen takim ismini NBA API ID'sine dönüştürür."""
    nba_teams = teams.get_teams()
    team_name_lower = team_name.lower()
    
    # isim eşleşmesi
    for t in nba_teams:
        if t['full_name'].lower() == team_name_lower:
            return t['id']
            
    # İstisnai eşleşmeler 
    if "clippers" in team_name_lower: return 1610612746
    if "knicks" in team_name_lower: return 1610612752
    if "76ers" in team_name_lower or "sixers" in team_name_lower: return 1610612755
    if "trail blazers" in team_name_lower: return 1610612757
    
    # Kısmi eşleşme
    for t in nba_teams:
        if t['nickname'].lower() in team_name_lower:
            return t['id']
            
    return 1610612747 # eşleşmezse varsayılan

# ana ekran
@app.get("/games", response_model=List[GamePreviewResponse])
def get_games_list(
    start_date: Optional[str] = Query(None, description="Başlangıç tarihi (YYYY-MM-DD)"),
    end_date: Optional[str] = Query(None, description="Bitiş tarihi (YYYY-MM-DD)")
):
    try:
        if not start_date:
            start_date = datetime.now().strftime("%Y-%m-%d")
        if not end_date:
            end_date = start_date

        start = datetime.strptime(start_date, "%Y-%m-%d")
        end = datetime.strptime(end_date, "%Y-%m-%d")

        if (end - start).days > 7:
            raise HTTPException(status_code=400, detail="Maksimum 7 gün seçilebilir.")

        games_list = []
        current_date = start

        while current_date <= end:
            # YYYY
            espn_date_str = current_date.strftime("%Y%m%d")
            display_date_str = current_date.strftime("%Y-%m-%d")
            
            try:
                url = f"http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates={espn_date_str}"
                response = requests.get(url, timeout=5)
                data = response.json()
                
                events = data.get('events', [])
                for event in events:
                    game_id = str(event['id'])
                    match_time = event['status']['type']['detail']
                    
                    # Arena Bilgisi
                    try:
                        arena_name = event['competitions'][0]['venue']['fullName']
                    except:
                        arena_name = "Bilinmeyen Arena"
                        
                    # Takımları ayıkla
                    competitors = event['competitions'][0]['competitors']
                    home_team = "Bilinmeyen"
                    away_team = "Bilinmeyen"
                    
                    for comp in competitors:
                        if comp['homeAway'] == 'home':
                            home_team = comp['team']['displayName']
                        else:
                            away_team = comp['team']['displayName']

                    games_list.append(GamePreviewResponse(
                        gameId=game_id,
                        homeTeam=home_team,
                        awayTeam=away_team,
                        matchDate=display_date_str,
                        matchTime=match_time,
                        arena=arena_name
                    ))
            except Exception as e:
                print(f"{espn_date_str} tarihi çekilirken hata oluştu: {e}")
                
            current_date += timedelta(days=1)
            
        return games_list

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

#Detay Ekranı
@app.get("/games/{game_id}/detail", response_model=GameDetailResponse)
def get_game_detail(game_id: str):
    try:
        # Maç çekme
        url = f"http://site.api.espn.com/apis/site/v2/sports/basketball/nba/summary?event={game_id}"
        response = requests.get(url, timeout=5)
        data = response.json()
        
        header = data.get('header', {}).get('competitions', [{}])[0]
        
        match_date = header.get('date', '')[:10]
        match_time = header.get('status', {}).get('type', {}).get('detail', 'Bilinmiyor')
        
        # Arena Şehir Bilgisi
        game_info = data.get('gameInfo', {})
        venue_info = game_info.get('venue', {})
        
        arena_name = venue_info.get('fullName', 'Bilinmiyor')
        city_name = venue_info.get('address', {}).get('city', 'Bilinmiyor')
        
        competitors = header.get('competitors', [])
        home_team_name = "Home"
        away_team_name = "Away"
        
        for comp in competitors:
            if comp.get('homeAway') == 'home':
                home_team_name = comp['team']['displayName']
            else:
                away_team_name = comp['team']['displayName']

        # convert
        home_team_id = get_nba_api_team_id(home_team_name)
        away_team_id = get_nba_api_team_id(away_team_name)

        # Son 5 Maçın Formunu ve Temel İstatistiklerini Çek
        try:
            home_log = teamgamelog.TeamGameLog(team_id=home_team_id).get_data_frames()[0].head(5)
            away_log = teamgamelog.TeamGameLog(team_id=away_team_id).get_data_frames()[0].head(5)
            
            home_form_str = "".join(home_log['WL'].tolist()) 
            away_form_str = "".join(away_log['WL'].tolist())
        except Exception:
            home_form_str, away_form_str = "Bilinmiyor", "Bilinmiyor"
            home_log = pd.DataFrame()
            away_log = pd.DataFrame()

        # istatistikler
        try:
            home_adv = teamdashboardbygeneralsplits.TeamDashboardByGeneralSplits(
                team_id=home_team_id, 
                measure_type_detailed_defense='Advanced', 
                last_n_games=5
            ).get_data_frames()[0]
            
            away_adv = teamdashboardbygeneralsplits.TeamDashboardByGeneralSplits(
                team_id=away_team_id, 
                measure_type_detailed_defense='Advanced', 
                last_n_games=5
            ).get_data_frames()[0]

            home_off_rtg = home_adv['OFF_RATING'].iloc[0] if not home_adv.empty else 115.0
            home_def_rtg = home_adv['DEF_RATING'].iloc[0] if not home_adv.empty else 115.0
            home_pace = home_adv['PACE'].iloc[0] if not home_adv.empty else 98.5
            home_ts = home_adv['TS_PCT'].iloc[0] if not home_adv.empty else 0.580
            
            away_off_rtg = away_adv['OFF_RATING'].iloc[0] if not away_adv.empty else 115.0
            away_def_rtg = away_adv['DEF_RATING'].iloc[0] if not away_adv.empty else 115.0
            away_pace = away_adv['PACE'].iloc[0] if not away_adv.empty else 98.5
            away_ts = away_adv['TS_PCT'].iloc[0] if not away_adv.empty else 0.580
            
        except Exception as e:
            print(f"⚠️ Gelişmiş veri çekilemedi: {e}")
            home_off_rtg, home_def_rtg, home_pace, home_ts = 115.0, 115.0, 98.5, 0.580
            away_off_rtg, away_def_rtg, away_pace, away_ts = 115.0, 115.0, 98.5, 0.580

        # Model için parametreler
        if model:
            expected_features = model.feature_names_in_
            feature_dict = {}
            
            for feat in expected_features:
                val = 0.0 
                
                if 'teamScore' in feat and 'home' in feat: val = home_log['PTS'].mean() if not home_log.empty else 110.0
                elif 'reboundsTotal' in feat and 'home' in feat: val = home_log['REB'].mean() if not home_log.empty else 43.0
                elif 'assists' in feat and 'home' in feat: val = home_log['AST'].mean() if not home_log.empty else 25.0
                elif 'turnovers' in feat and 'home' in feat: val = home_log['TOV'].mean() if not home_log.empty else 13.0
                elif 'fieldGoalsPercentage' in feat and 'home' in feat: val = home_log['FG_PCT'].mean() if not home_log.empty else 0.460
                
                elif 'teamScore' in feat and 'away' in feat: val = away_log['PTS'].mean() if not away_log.empty else 110.0
                elif 'reboundsTotal' in feat and 'away' in feat: val = away_log['REB'].mean() if not away_log.empty else 43.0
                elif 'assists' in feat and 'away' in feat: val = away_log['AST'].mean() if not away_log.empty else 25.0
                elif 'turnovers' in feat and 'away' in feat: val = away_log['TOV'].mean() if not away_log.empty else 13.0
                elif 'fieldGoalsPercentage' in feat and 'away' in feat: val = away_log['FG_PCT'].mean() if not away_log.empty else 0.460
                
                elif 'offensiveRating' in feat and 'home' in feat: val = home_off_rtg
                elif 'defensiveRating' in feat and 'home' in feat: val = home_def_rtg
                elif 'pace' in feat and 'home' in feat: val = home_pace
                elif 'trueShooting' in feat and 'home' in feat: val = home_ts
                
                elif 'offensiveRating' in feat and 'away' in feat: val = away_off_rtg
                elif 'defensiveRating' in feat and 'away' in feat: val = away_def_rtg
                elif 'pace' in feat and 'away' in feat: val = away_pace
                elif 'trueShooting' in feat and 'away' in feat: val = away_ts
                
                elif 'netRating' in feat and 'home' in feat: val = home_off_rtg - home_def_rtg
                elif 'netRating' in feat and 'away' in feat: val = away_off_rtg - away_def_rtg
                
                feature_dict[feat] = [val]
            
            input_df = pd.DataFrame(feature_dict)[expected_features]
            probs = model.predict_proba(input_df)[0]
            home_prob = int(round(float(probs[1]) * 100))
        else:
            home_prob = 50 

        away_prob = 100 - home_prob
        predicted_winner = home_team_name if home_prob >= 50 else away_team_name

        return GameDetailResponse(
            homeTeam=home_team_name,
            awayTeam=away_team_name,
            matchDate=match_date,
            matchTime=match_time,
            winner=predicted_winner,
            homeProbability=home_prob,
            awayProbability=away_prob,
            arena=arena_name,
            city=city_name,
            homeForm=home_form_str,
            awayForm=away_form_str
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))