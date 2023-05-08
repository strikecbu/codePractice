import requests
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument('--prompt', type=str, help='What python script you want to do', required=True)
parser.add_argument('--output', type=str, default='output.py', help='Output file name')
args = parser.parse_args()

chat_url = 'https://api.openai.com/v1/completions'
open_api_key = os.getenv('PYTHON_OPENAI_API_KEY')

request_headers = {
    "Content-Type": "application/json",
    "Authorization": "Bearer " + open_api_key
}

request_body = {
    "model": "text-davinci-003",
    "prompt": f"Write python script for {args.prompt}, only code, no text.",
    "max_tokens": 70,
    "temperature": 0
}

response = requests.post(chat_url, headers=request_headers, json=request_body)


if response.status_code == 200:
    print(str(response.json()) + '\n')
    response_text = response.json()["choices"][0]["text"]
    with open(args.output, 'w') as f:
        f.write(response_text)
        print(f"Python script saved in output.py: \n{response_text}")
else:
    print(f"Something going wrong, status code: {response.status_code}")
    print(response.json())

