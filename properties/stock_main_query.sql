select DISTINCT symbol from stock_main where day='2016-09-01'

select symbol,open from stock_main where day='2016-09-01'

insert into stock_main_id(symbol) select symbol from stock_main where day='2016-09-01'

select c.symbol,c.incre from (select m.symbol,(n.close-m.close)/n.close incre
from (select a.symbol,b.close from stock_main_id a
LEFT JOIN stock_main b on a.symbol=b.symbol
where b.day='2000-01-05') m,
(select a.symbol,max(b.close) `close`
 from stock_main_id a
LEFT JOIN stock_main b on a.symbol=b.symbol
where b.day BETWEEN '2000-01-05' and '2000-05-19' GROUP BY a.symbol) n
where m.symbol=n.symbol) c where c.incre>0 ORDER BY c.incre DESC LIMIT 0,10


select c.symbol,c.increase from (select m.symbol,(n.close-m.close)/n.close increase   
from (select a.symbol,b.close from stock_main_id a   LEFT JOIN stock_main b on a.symbol=b.symbol   where b.day='2000-01-05') m, 
  (select a.symbol,max(b.close) `close`    from stock_main_id a   LEFT JOIN stock_main b on a.symbol=b.symbol   
where b.day BETWEEN '2000-01-05' and '2000-05-19' GROUP BY a.symbol) n   where m.symbol=n.symbol) c where c.increase>0 ORDER BY c.increase DESC limit 0,10


select * from stock_main where day='2000-02-20'
select * from stock_main where symbol='000023' limit 0,50

select a1.symbol,a1.sub1,a2.sub2 from (select aa.* from (select c.symbol,(c.max-d.min)/d.min as sub1,c.max,d.min from
(select  a.symbol,max(a.close) max
from stock_main a where a.day = '2015-01-05' GROUP BY a.symbol) c 
LEFT JOIN 
(select  b.symbol,min(b.close) min
from stock_main b where b.day BETWEEN ADDDATE('2015-01-05',INTERVAL -15 day) and '2015-01-05' GROUP BY b.symbol) d
on c.symbol=d.symbol) as aa  where aa.sub1<0.1) a1
,
(select aa.* from (select c.symbol,(c.max-d.min)/d.min as sub2,c.max,d.min from
(select  a.symbol,max(a.close) max
from stock_main a where a.day BETWEEN '2015-01-05' and ADDDATE('2015-01-05',INTERVAL 14 day) GROUP BY a.symbol) c 
LEFT JOIN 
(select  b.symbol,min(b.close) min
from stock_main b where b.day ='2015-01-05' GROUP BY b.symbol) d
on c.symbol=d.symbol) as aa WHERE aa.sub2>0.1) a2
where a1.symbol=a2.symbol




