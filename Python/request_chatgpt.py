import requests

chat_url = 'https://api.openai.com/v1/completions'
open_api_key = 'YOUR_API_KEY'

request_headers = {
    "Content-Type": "application/json",
    "Authorization": "Bearer " + open_api_key
}

request_body = {
    "model": "text-davinci-003",
    "prompt": "Write Java hello word demo",
    "max_tokens": 70,
    "temperature": 0
}

response = requests.post(chat_url, headers=request_headers, json=request_body)


if response.status_code == 200:
    print(str(response.json()) + '\n')
    print(response.json()["choices"][0]["text"])
else:
    print(f"Something going wrong, status code: {response.status_code}")
    print(response.json())

