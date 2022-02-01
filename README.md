# crypto-watcher
# REST service for viewing cryptocurrency quotes and receiving notifications about changes
# Technologies used
* Spring, Spring Boot, Spring Data JPA
* MySql
* Slf4j
* Lombok
* Quartz

# Info
List of currencies:
[ {"id":"90","symbol":"BTC"}, {"id":"80","symbol":"ETH"}, {"id":"48543","symbol": ”SOL”} ]

* GET /api/cryptocurrencies - load available cryptos
* GET /api/cryptocurrency/{id} - load info about cryptocurrency by id
* POST /api/cryptocurrency/notify (username, cryptoSymbol) - subscribe to cryptocurrency changes

Once a minute, the current prices for available cryptocurrencies are requested from an external source CoinLore and stored in the database.
The log file api_logs.log records messages about changes in currencies by more than 1%. To do this, the user registers himself using the notify REST method and passes his name (username) and cryptocurrency code (symbol). At the time of registration, the current price of the specified cryptocurrency is saved with reference to the user. As soon as the current price for the specified currency has changed by more than 1%., a WARN level message is displayed in the server log, which contains: currency code, username and percentage of price change since registration.
