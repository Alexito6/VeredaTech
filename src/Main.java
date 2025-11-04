import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Document document;
        try {
            document = GestorInventarioDOM.cargarXML("inventario.xml");
            GestorInventarioDOM.buscarProductosStockBajo(20);

            GestorInventarioDOM.actualizarStock(document,"P104",50);
            GestorInventarioDOM.anadirProducto(document,"P105","Monitor","Monitor Deluxe God","Sanic Speed",100.5,6);
            GestorInventarioDOM.eliminarProducto(document,"P101");

            GestorInventarioDOM.guardarXML(document,"inventario_modificado.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GestorInventarioDOM.mostrarInventario(document);
    }
}
