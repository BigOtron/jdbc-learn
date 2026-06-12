package dev.shermuhammad.custom_type;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class Point implements SQLData {
    private String sqlTypeName;
    private int x;
    private int y;


    @Override
    public String getSQLTypeName() throws SQLException {
        return sqlTypeName;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.sqlTypeName = typeName;
        this.x = stream.readInt();
        this.y = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.x);
        stream.writeInt(this.y);
    }

    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
