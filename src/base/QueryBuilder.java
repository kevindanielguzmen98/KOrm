package base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creador de consultas SQL
 * 
 * @author kevindanielguzmen98@gmail.com
 * @version 0.0.1
 * @since 2018-04-30
 * 
 * @property select Cadena con las columas a retornar del la consulta
 * @property from Cadena con las tablas de las cuales desea consultar los datos
 * @property where Cadena con las sentencias de la consulta
 * @property limit Limite de registro que serán retornados por la consulta
 * @property offset Cursor de registro
 */
public class QueryBuilder {

    public String select = "";
    public String from = "";
    public String where = "";
    public int limit = -2;
    public int offset = -2;
    public String[] join = null;

    /**
     * Set de las columnas para la consulta
     * @param columns
     * @return QueryBuilder
     * @throws Exception 
     */
    public QueryBuilder select(String columns) throws Exception {
        if (columns.length() > 0) {
            this.select = columns;
        } else {
            throw new Exception("Las columnas no pueden estar vacias");
        }
        return this;
    }
    
    /**
     * Set de la(s) tabla(s) para la consulta
     * @param table
     * @return QueryBuilder
     * @throws Exception 
     */
    public QueryBuilder from(String table) throws Exception {
        if (table.length() > 0) {
            this.from = table;
        } else {
            throw new Exception("Las tablas no pueden estar vacias");
        }
        return this;
    }

    /**
     * Set de los criterios para la consulta
     * @param whereString
     * @return QueryBuilder
     * @throws Exception 
     */
    public QueryBuilder where(String whereString) throws Exception {
        if (whereString.length() > 0) {
            this.where = whereString;
        } else {
            throw new Exception("Los criterios para la consulta no pueden estar vacias");
        }
        return this;
    }

    /**
     * Set limite de registro para la consulta
     * @param limit
     * @return QueryBuilder
     * @throws Exception 
     */
    public QueryBuilder limitOffset(int limit) throws Exception {
        this.limit = limit;
        return this;
    }

    /**
     * Set del cursor para la consulta
     * @param offset
     * @return QueryBuilder
     */
    public QueryBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }
    
    public QueryBuilder innerJoin(String table, String sentence) {
        this.join = new String[]{ "INNER", table, sentence };
        return this;
    }

    /**
     * Ensambla la consulta con todos los datos disponibles
     * @return String
     */
    public String assembleQuery() {
        String queryString = "SELECT " + this.select + " FROM " + this.from;
        if (this.join != null) {
            queryString = queryString + " " + this.join[0] + " JOIN " + this.join[1] + " ON " + this.join[2];
        }
        if (this.where.length() > 0) {
            queryString = queryString + " WHERE " + this.where;
        }
        if (this.limit > -2) {
            queryString = queryString + " LIMIT " + this.limit;
        }
        if (this.limit > -2) {
            queryString = queryString + " OFFSET " + this.offset;
        }
        queryString = queryString + ";";
        return queryString;
    }

    /**
     * Ejecuta la consulta
     * @return ResultSet
     */
    public ResultSet execute() {
        ResultSet result = null;
        try {
            Statement query = Connection.openConnection().createStatement();
            result = query.executeQuery(this.assembleQuery());
        } catch (SQLException ex) {
            Logger.getLogger(QueryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Ejecuta la consulta y retorna un ArrayList con los registros de la consulta
     * @return ArrayList
     */
    public ArrayList executeToArray() {
        ArrayList rows = new ArrayList();
        try {
            ResultSet result = this.execute();
            ResultSetMetaData numberColumns = result.getMetaData();
            while (result.next()) {
                ArrayList row = new ArrayList();
                for (int i=1; i <= numberColumns.getColumnCount(); i ++) {
                    row.add(result.getString(i));
                }
                rows.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
    
    
}
