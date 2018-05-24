#!/bin/bash

bot_file=/home/stefano/Documents/bot.txt
while read -r line || [[ -n "$line" ]]; do
    curl -X POST http://localhost:8080/bots/create?twitterName=${line}
done < $bot_file

users_file=/home/stefano/Documents/user.txt
while read -r line || [[ -n "$line" ]]; do
    curl -X POST http://localhost:8080/bots/createUser?twitterName=${line}
done < $users_file