## Exercise 4. Pig.

#### Introduction.
We are going to use the New York Stock Exchange (NYSE) dataset in this exercise. This dataset includes the daily open, close, high and low volume on the NYSE, as well as stock dividends. The data fields available include date, stock symbol, stock open and close prices, highs, lows, volume, and adjusted closing price. The dataset includes data from NYSE from 1970 to 2010. We are going to be doing a small-scale analysis and focus on daily prices and dividends of stocks for symbols starting with `A`&#185;. 

#### Dataset preparation.
In the text of the exercise it is assumed that you have the dataset files (`NYSE_dividends_A.csv`, `NYSE_daily_prices_A.csv`) in the (docker) volume mapped to `/ma120/exercise4_pig`. Change this path if you have different configuration accordingly.

#### Tasks.
Pig is already installed in the docker image so you can run your experiments there. At the time of writing, the installed version is the latest, i.e. `0.16.0`. Start the pig with `pig -x local`.

If you wish to experiment with it on your machine, you can install it following the steps described in the [Appendix A](#appendix-a) at the end of this document. 

* Load subset for stocks:
```
       STOCK_A = LOAD '/ma120/exercise4_pig/NYSE_daily_prices_A.csv' using PigStorage(',');
```
Check the `STOCK_A`:
```
       DESCRIBE STOCK_A;
```
* Check the schema. What did you get? Why does Pig tell us that the schema is unknown?
* Load it with a schema.
```
       STOCK_A = LOAD '/ma120/exercise4_pig/NYSE_daily_prices_A.csv' using PigStorage(',') AS (exchange:chararray, symbol:chararray, date:chararray, open:float, high:float, low:float, close:float, volume:int, adj_close:float); 
```
Check the `STOCK_A`:       

       DESCRIBE STOCK_A;`
* Create a subset of the data. 100 rows.
```
       B = LIMIT STOCK_A 100;
       DESCRIBE B;
```
* View the relation. Since `B` contains only 100 relations, we can simply dump its contents to standard out.
```
       DUMP B;
```
* One of the key uses of Pig is data transformation. You can define a new relation based on the fields of an existing relation using the `FOREACH` command. Define a new relation `C`, which will contain only the symbol, date and close fields from relation `B`.
```
       C = FOREACH B GENERATE symbol, date, close;
```
Now, check the schema:
```
       DESCRIBE C;
```
* Dump the content of `C` to see if the transformed relation has expected data.
```       
       DUMP C;
```
* Performing a join with dividents data. First we load dividents for `A`.
```
       DIV_A = LOAD '/ma120/exercise4_pig/NYSE_dividends_A.csv' using PigStorage(',') AS (exchange:chararray, symbol:chararray, date:chararray, dividend:float);
```
* Join two relations `STOCK_A` and `DIV_A`:
```
       JOINED_REL = JOIN STOCK_A BY (symbol, date), DIV_A BY (symbol, date);
```
* Check the schema of joined relation:
```
       DESCRIBE JOINED_REL;
```
* Check the content stored in `JOINED_REL`.
```
       DUMP JOINED_REL;
```       
* Sort the data with order by:
```
       SORTED_DIV_A = ORDER DIV_A BY symbol, date asc; 
```
Check the content:
```
       DUMP SORTED_DIV_A;
```
* The `GROUP` command allows you to group a relation by one of its fields. Enter the following commands, which groups the `DIV_A` relation by the dividend price for the "AZZ" stock.
Filter first:

       B = FILTER DIV_A BY symbol=='AZZ';
Then, group:
```
       C = GROUP B BY dividend; 
       DESCRIBE C;
       DUMP C;
```
Notice that the data for stock symbol "AZZ" is grouped together for each dividend.
-------

&#185;. If you are interested on the whole dataset, you can download it at: https://s3.amazonaws.com/hw-sandbox/tutorial1/infochimps_dataset_4778_download_16677-csv.zip


### Appendix A. 
### Pig installation instruction on local machine.
This step is optional.

1. Download Pig at : <http://apache.uib.no/pig/pig-0.16.0/pig-0.16.0.tar.gz>
2. Setup the path environmental variable. It should include `<pig_folder>/bin`
3. You can now execute `pig -x local` (you may need to reopen your terminal/shell window).

