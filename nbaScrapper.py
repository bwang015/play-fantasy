#!/usr/local/bin/python3
import requests
import json
from bs4 import BeautifulSoup

#Open file containing player names
file = open('players.txt', 'r')

for name in file:
    name = name.rstrip()
    url = "http://localhost:9000/player"
    headers = {'Content-Type': 'application/json'}
    obj = {"name": name}
    params = json.dumps(obj).encode('utf8')
    response = requests.post(url, headers = headers, data = params)


# #Get the endpoint
# endpoint = "https://www.basketball-reference.com/players/d/duranke01/gamelog/2017"

# #Save the endpoint into response object and parses it into soup object
# response = requests.get(endpoint)
# soup = BeautifulSoup(response.content, "html.parser")

# #Save the season table into table
# table = soup.find_all("table", class_="row_summable sortable stats_table")[0]

# #Split the tables into rows and filter by active games
# rows = table.findChildren("tr", id=True)

# jsonArray = []
# for row in rows:
#     jsonObj = {}
#     arr = list(row)
#     jsonObj["Game Number"] = arr[0].text
#     jsonObj["Games Played"] = arr[1].text
#     jsonObj["Date"] = arr[2].text
#     jsonObj["Team"] = arr[4].text
#     jsonObj["Away"] = arr[5].text
#     jsonObj["Opponent"] = arr[6].text
#     jsonObj["Minutes Played"] = arr[9].text
#     jsonObj["Field Goal Made"] = arr[10].text
#     jsonObj["Field Goal Attempted"] = arr[11].text
#     jsonObj["Three Pointers Made"] = arr[13].text
#     jsonObj["Three Pointers Attempted"] = arr[14].text
#     jsonObj["Free Throws Made"] = arr[16].text
#     jsonObj["Free Throws Attempted"] = arr[17].text
#     jsonObj["Offensive Rebounds"] = arr[19].text
#     jsonObj["Defensive Rebounds"] = arr[20].text
#     jsonObj["Assists"] = arr[22].text
#     jsonObj["Steals"] = arr[23].text
#     jsonObj["Blocks"] = arr[24].text
#     jsonObj["Turnovers"] = arr[25].text
#     jsonObj["Personal Fouls"] = arr[26].text
#     jsonObj["Points"] = arr[27].text
#     jsonObj["Plus Minus"] = arr[29].text
#     jsonArray.append(jsonObj)

# jsonArrayObject = json.dumps(jsonArray)
# url = "http://localhost:9000/upload/player/"
# headers = {'Content-Type': 'application/json'}
#     obj = {"name": name}
#     params = json.dumps(obj).encode('utf8')
#     r = requests.post(url, headers = headers, data = params)
# print(jsonArrayObject)