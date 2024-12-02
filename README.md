# AOC-SLACK

Slack bot for Advent of Code.

## Features

### Posting messages about solved puzzles to Slack channels in real time.

![img.png](stars.png)

### Posting a message with leaderboard at the end of the day.

![img_2.png](leaderboard.png)

## Installation

To install slack bot you need to add slack app to your workspace and host aoc-slack bot somewhere.

### Add app to your slack workspace

1. Go to https://api.slack.com/apps and create a new app.
2. Select "From scratch" and give it a name and workspace.
3. Select incoming webhooks and activate it.
4. Click "Add new webhook to your workspace" and select a channel to which you want to receive messages.

### Host aoc-slack bot

To run aoc-slack in docker you need to run the following command:

```shell
docker run -e AOC_SLACK_LEADERBOARD_ID=<leaderboard_id> \
           -e AOC_SLACK_SESSION=<aoc_session> \
           -e AOC_SLACK_WEBHOOK_URL=<slack_app_webhook_url> \
           -d \
           1grzyb1/aoc-slack:latest
```

or use example `docker-compose.yml`. Pass there your variables and just run `docker-compose up -d`

#### Properties description

##### **Required Parameters**

| Property                              | Description                                      |
|---------------------------------------|--------------------------------------------------|
| AOC_SLACK_LEADERBOARD_ID _(required)_ | ID of your private leaderboard in Advent of Code |
| AOC_SLACK_SESSION _(required)_        | Session header from Advent of Code website       |
| AOC_SLACK_WEBHOOK_URL _(required)_    | URL to Slack app webhook                         |

---

##### **Optional Parameters**

| Property                    | Description                                                             | Default Value        |
|-----------------------------|-------------------------------------------------------------------------|----------------------|
| AOC_SLACK_YEAR              | Year for which bot should work                                          | Current year         |
| AOC_SLACK_STARS_CRON        | Cron schedule for refreshing new stars                                  | Every 15 minutes     |
| AOC_SLACK_LEADERBOARD_CRON  | Cron schedule for sending the leaderboard                               | Midnight EST (UTC-5) |
| AOC_SLACK_ONLY_ACTIVE_USERS | Shows only users with points on the leaderboard                         | False                |
| AOC_SLACK_USE_STARS         | Sends leaderboard with stars instead of local score if set to true      | False                |
| AOC_SLACK_MAX_RETRIES       | Maximum number of retries for the Advent of Code API                    | 3                    |
| AOC_SLACK_MIN_BACKOFF       | Minimum backoff duration for the first retry call to Advent of Code API | 5s                   |