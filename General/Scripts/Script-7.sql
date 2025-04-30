use world;

select c.ID, c.NAME
from Country p
join City c on p.code=c.CountryCode 
where upper(p.Name) = "COLOMBIA"
order by Name asc
limit 10;