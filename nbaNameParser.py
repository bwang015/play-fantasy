#!/usr/local/bin/python3
import requests
import json
from bs4 import BeautifulSoup

#Get the endpoint
endpoint = "https://www.basketball-reference.com/players/z/"

#Save the endpoint into response object and parses it into soup object
response = requests.get(endpoint)
soup = BeautifulSoup(response.content, "html.parser")

#Save the season table into table
table = soup.find_all("table", class_="sortable stats_table")[0]

#Split the tables into rows and filter by active games
rows = table.findChildren("tr")

for row in rows:
    if not row.strong:
        continue
    #Get Player Name
    name = row.a.text
    #Get Player ID
    id = row.a.get('href').split('/')[3].split('.')[0]
    print("Attempting to add " + name + " with ID: " + id)
    url = "http://localhost:9000/player/" + id
    headers = {'Content-Type': 'application/json'}
    obj = {"name": name}
    params = json.dumps(obj).encode('utf8')
    r = requests.put(url, headers = headers, data = params)
    print(r.status_code)