# StatisticCollector Project

Overview:
=
This project collects data for currency and they're rates provided by [Fixer.io](https://fixer.io/) and have functionality to request for specific currency the latest rate or historical rates for specific period of time.

Infratsructure:
=

Functionality:
=

1. On every 5min(sheduled time that can be change) we invoke Fixer.io API to get latest rates update for currencies
   - if the currency does not exist - it's created in the DB
   - if the currency exist and in the latest rate in the DB is different - save the new rate in another row into the DB
   - if the currency exist and in the latest rate in the DB is the same - don't do anything
2. Different POST endpoints to make requests to the System
   - XML API - with one endpoint that can get lates rate for currency or historical rates for specific period for currency
   - JSON API - with two endpoints - one for latest rate for currency, and another one for historical rates for specific period for currency
3. Unique external service requests - Redis checks for dublicate request ids and block the identical ids for 5min
4. Save statistics data - on every invoke to the System the statistic data for external service request is stored into the DB and this information is publish like a message in RabbitMQ

Used technology:
=
Java 17, SpringBoot 3.2.3, Redis, RabbitMQ, MySQL

**For Redis, RabitMQ and MySQL**
I create and run them in Docker and use docker dekstop to monitor the statuses.

**Redis:**
```
 docker run --name redis-collector -p 6379:6379 -d redis
```

**RabbitMQ:**
```
docker run --name rabbitmq-server -p 5672:5672 -p 15672:15672 --network app-network -d rabbitmq:3-management
```

**MySQL:**
```
docker run --name mysql-db -e MYSQL_ROOT_PASSWORD=kovachka -e MYSQL_DATABASE=gateway-statistics -e MYSQL_USER=kovachka -e MYSQL_PASSWORD=kovachka --network app-network -p 3306:3306 -d mysql:latest
```

Application properties:
=
In the [application.properties](https://github.com/AleksandrinaK/StatisticCollector/blob/main/src/main/resources/application.properties) file are stored all usernames, passwords, ports, configarations and my Fixer.io API Key (it's with 100 requests free trial - most of them are already used)

Table structure:
=

**Table: Currency**
```
id - primary key, auto increment value for the table
currency_code - currency code the way that is fetched from Fixer.io
```

**Table: Rate**
```
rate_id - primary key, auto increment value for every updated rate for currency
date - the date when the rate is fetched from Fixer.io
rate - updated rate
timestamp - the date and the time when the rate is fetched from Fixer.io
currency_id - foreign key (the id from Currency table)
```

**Table: Statistics**
```
reqiest_id - unique reqiest id that comes with the request from the external service
client_id - client id (identificator id for the client)
service_name - the name of the external service (two options 'EXT_SERVICE_1','EXT_SERVICE_2')
time - date and time of the request from the external service
```


XML API endpoint:
=

Post request with url: **http://localhost:8080/xml_api/command** (I'm using localhost for now for local testing)

Request body for latest rate for currency:
```
<command id="1234" >
    <get consumer="13617162" >
        <currency>EUR</currency>
    </get>
</command>
```
Expected response:
```
<result>
    <currency>EUR</currency>
    <latest>
        <rate>1</rate>
    </latest>
</result>
```

Request body for historical rates for specific period for currency:
```
<command id="1234-8785" >
    <history consumer="13617162" currency="AED" period="24"/>
</command>
```

Expected response:
```
<result>
    <currency>AED</currency>
    <latest>
        <rate>4.016715</rate>
        <rate>4.017681</rate>
        <rate>4.018196</rate>
        <rate>4.015613</rate>
        <rate>4.016318</rate>
        <rate>4.015131</rate>
        <rate>4.015003</rate>
        <rate>4.013111</rate>
        <rate>3.987773</rate>
        <rate>3.98732</rate>
        <rate>3.986981</rate>
        <rate>3.987247</rate>
        <rate>3.987387</rate>
        <rate>3.987645</rate>
        <rate>3.987598</rate>
        <rate>3.988489</rate>
        <rate>3.987857</rate>
        <rate>3.988119</rate>
        <rate>3.988049</rate>
        <rate>3.988465</rate>
    </latest>
</result>
```

JSON API endpoints:
=

**Post request for latest rate for currency:**

URL: **http://localhost:8080/json_api/current**

Request Body:
```
{
  "requestId": "b89577fe-8c37-4962-8af3-7cb89a245160",
  "timestamp": 1711005249,
  "client": "1234",
  "currency": "AED"
}
```

Expected response:
```
{
    "currency": "AED",
    "rate": "3.989561"
}
```

**Post request for for historical rates for specific period for currency:**

URL: **http://localhost:8080/json_api/history**

Request Body:
```
{
"requestId": "b89577fe-8c37-4962-8af3-7cb89a24q908",
"timestamp": 1711005249,
"client": "1234",
"currency":"AED",
"period": 24
}
```

Expected response:
```
{
    "currency": "AED",
    "updates": [
        {
            "rate": "4.016715",
            "timestamp": "2024-03-21T01:47:05"
        },
        {
            "rate": "4.017681",
            "timestamp": "2024-03-21T09:05:06"
        },
        {
            "rate": "4.018196",
            "timestamp": "2024-03-21T09:12:04"
        },
        {
            "rate": "4.015613",
            "timestamp": "2024-03-21T09:22:06"
        },
        {
            "rate": "4.016318",
            "timestamp": "2024-03-21T09:27:06"
        },
        {
            "rate": "4.015131",
            "timestamp": "2024-03-21T09:37:04"
        },
        {
            "rate": "4.015003",
            "timestamp": "2024-03-21T09:42:05"
        },
        {
            "rate": "4.013111",
            "timestamp": "2024-03-21T09:47:05"
        },
        {
            "rate": "3.987773",
            "timestamp": "2024-03-21T18:24:03"
        },
        {
            "rate": "3.98732",
            "timestamp": "2024-03-21T19:46:05"
        },
        {
            "rate": "3.986981",
            "timestamp": "2024-03-21T19:51:04"
        },
        {
            "rate": "3.987247",
            "timestamp": "2024-03-21T19:56:04"
        },
        {
            "rate": "3.987387",
            "timestamp": "2024-03-21T20:00:03"
        },
        {
            "rate": "3.987645",
            "timestamp": "2024-03-21T20:05:03"
        },
        {
            "rate": "3.987598",
            "timestamp": "2024-03-21T20:10:03"
        },
        {
            "rate": "3.988489",
            "timestamp": "2024-03-21T20:15:03"
        },
        {
            "rate": "3.987857",
            "timestamp": "2024-03-21T20:18:03"
        },
        {
            "rate": "3.988119",
            "timestamp": "2024-03-21T20:20:04"
        },
        {
            "rate": "3.988049",
            "timestamp": "2024-03-21T20:25:03"
        },
        {
            "rate": "3.988465",
            "timestamp": "2024-03-21T20:30:04"
        }
    ]
}
```
