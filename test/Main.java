
import base.QueryBuilder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal
 *
 * @author kevindanielguzmen98@gmail.com
 * @version 0.0.1
 * @since 2018-04-29
 */
public class Main {

    public static void main(String args[]) {
        try {
            ResultSet result = null;
            result = new QueryBuilder().select("*").from("persona").execute();
            while (result.next()) {
                System.out.println(result.getString("nombre"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ArrayList rows = new QueryBuilder().select("persona.*, localidad.*")
                    .from("persona").innerJoin("localidad", "persona.ciudad_id=localidad.id")
                    .executeToArray();
            for (Iterator it = rows.iterator(); it.hasNext();) {
                ArrayList row = (ArrayList) it.next();
                System.out.println(row.toString());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
