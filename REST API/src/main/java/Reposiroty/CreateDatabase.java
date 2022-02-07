package Reposiroty;

import java.util.ArrayList;
import java.util.List;

public class CreateDatabase {
    public static String createDatabaseSQL(){
        return "DROP TABLE IF EXISTS demo; CREATE TABLE demo (ID integer primary key, Name varchar(20), Hint varchar(200) );";
    }

    public static List<String> loadData(){
        List<String> array = new ArrayList<>();
        array.add("insert into demo values(1,'SQL Online','for Data Science');");
        array.add("insert into demo values(2,'Kirill N.','https://www.linkedin.com/in/sqliteonlinecom');");
        array.add("insert into demo values(3,'Twitter','https://twitter.com/SqliteOnlineCom');");
        array.add("insert into demo values(4,'Chart','LINE-SELECT name, cos(id), sin(id) FROM demo');");
        array.add("insert into demo values(6,'SqLite 3.36.0','SQL OnLine on JavaScript');");
        array.add("insert into demo values(7,'[RightClick] mouse','Opens many additional features');");
        array.add("insert into demo values(8,'Left-Panel, Table','[RightClick] mouse ''Context menu');");
        array.add("insert into demo values(9,'Tabs','mouse: [RightClick] , [MiddleClick] , [Wheel] , [LeftClick]');");
        return array;
    }
}
