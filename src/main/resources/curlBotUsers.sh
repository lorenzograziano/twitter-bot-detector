#!/bin/bash

bot_file=/Users/fabriziocairo/bot.txt
while read -r line || [[ -n "$line" ]]; do
    curl -X POST http://localhost:8080/bots/create?twitterName=line
done < $bot_file

users_file=/Users/fabriziocairo/user.txt
while read -r line || [[ -n "$line" ]]; do
    curl -X POST http://localhost:8080/bots/createUser?twitterName=line
done < $users_file