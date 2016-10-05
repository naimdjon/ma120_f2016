## Exercise 6. Hive.

Pull the newest image `naimdjon/hadoop`. 
```bash
docker pull naimdjon/hadoop && \
docker run -it --name ma120 naimdjon/hadoop
```
Start HDFS daemon using:
`bash /opt/hadoop/latest/sbin/start-dfs.sh`. There are two files that we are going to use in this exercise. These are located in `/root/` folder. You must copy these files over to HDFS to be able to use them from Hive. So do it first before starting the exercise. Tip: copy it to root's home directory on HDFS.

1. Initialize schema using the following command:
`schematool -initSchema -dbType derby`
This will take a bit time to create the schema for the chosen database.

When finished, type `hive`, it should bring you to the the shell. 

2. Create your database. First check the list of databases:
    `show databases;`
    You should see some databases listed
3. Create your database:
    `create database <your_database_name>;` 
    You can use your username for the database name. Once you executed this command, check if your database is in the list of existing databases.
4. Change the database. 
    `use <your_database_name>;`
5. Check if there are tables in your database. There should be none: `show tables;`.

6. Create a table for daily_prices for A. NB! `exchange` and `date` are reserved keywords, so we use xchange and d_date.

```
CREATE TABLE daily_prices (xchange STRING, symbol STRING, d_date STRING, open FLOAT, high FLOAT, low FLOAT, close FLOAT, volume INT, adj_close FLOAT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
```

7. Check the list of tables, describe your newly created table using `describe` command.

8. We have defined the table, now we are going to load the data into the table.
```
LOAD DATA INPATH '/user/root/NYSE_daily_prices_A.csv' OVERWRITE INTO TABLE daily_prices;
```

9. How many rows are there? Use the standard sql statement for that:
`select count(*) from daily_prices;`
Your result should be 735027.

10. What was the closing value and the volume for ticker ABC on the April 25th 1996? To answer this you have to create a query with the specified criteria. To check your answer, see [1] in the bottom for the answer. 
 
11. Create a table for dividends. 
```
CREATE TABLE dividends (xchange STRING, symbol STRING, d_date STRING, dividend FLOAT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
```
Check how many tables you have got now. 

12. Load dividends data for A:
```
LOAD DATA INPATH '/user/root/NYSE_dividends_A.csv' OVERWRITE INTO TABLE dividends;
```

13. When was the highest and the most recent dividend for stock symbol 'AYN'?
    See [2] for the answer.

13. When and which symbol had the highest dividend ever? Hint: use max function.
    See [3] for the answer. 

14. Join the two tables, to get a view of symbol, date and dividend,  the result should return only include the daily_prices to which there is a corresponding dividend. How many symbols had dividend?
 See [4] for the answer.

15. We are done now. Feel free to explore more functionality in Hive. When you are done, run the drop script.
Drop your database (unless you thought to keep it for further exploration later)

     `drop database <your_database_name>`



[1]: CLosing=37.50, volume=167200, the query is: `select close, volume from daily_prices where symbol='ABC' and d_date='1996-04-25';`

[2]: The answer is `2005-09-07`, `0.077`.
Query: we need to obtain the results in descending order:
```
select symbol, d_date, dividend from dividends where symbol='AYN' order by dividend desc, d_date desc limit 1;
```
[3]: The answer is AMR  34.958, the query is:
```
select symbol, max(dividend) as max_d from dividends group by symbol order by max_d desc limit 1;
```
[4]: The answer is:
```csv
AMR 34.958
ACV 32.3
ALE 16.0
ACO 14.0
ASH 12.62
AI  9.2
AYI 8.0
AI  7.8
AXP 7.16
AI  6.8
```
Query for this:
```
select dp.symbol, d.d_date, d.dividend from dividends d inner join daily_prices dp on d.symbol = dp.symbol group by dp.symbol, d.d_date, d.dividend order by dividend desc limit 10;
```
