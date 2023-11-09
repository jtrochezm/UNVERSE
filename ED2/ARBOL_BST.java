/**
 *
 * @author DavidMorales
 */


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ArbolBSTComparativo {
    public static void main(String[] args) {
        int[] tamanosDatos = {10000, 100000, 1000000, 10000000, 100000000};

        for (int tamano : tamanosDatos) {
            System.out.println("Tamaño de Datos: " + tamano);

            // Generar datos y almacenar en un archivo si no existe
            String archivoDatos = "datos_" + tamano + ".txt";
            if (!Files.exists(Paths.get(archivoDatos))) {
                generarDatos(archivoDatos, tamano);
            }

            // Crear un árbol BST
            ArbolBST arbol = new ArbolBST();

            // Medición de tiempos para la creación de datos
            medirTiempo("Inserción en Árbol BST", () -> cargarDatosEnArbol(arbol, archivoDatos, Charset.forName("ISO-8859-1")));

            // Medición de tiempos para otras operaciones (actualización, eliminación, búsqueda, consulta)
            medirTiempo("Actualización en Árbol BST", () -> actualizarDatoEnArbol(arbol));

            medirTiempo("Eliminación en Árbol BST", () -> eliminarDatoEnArbol(arbol));

            medirTiempo("Búsqueda en Árbol BST", () -> buscarDatoEnArbol(arbol));

            medirTiempo("Consulta de Todos los Datos en Árbol BST", () -> consultarTodosLosDatosEnArbol(arbol));

            System.out.println();
        }
    }
    private static void generarDatos(String archivo, int tamano) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            Random rand = new Random();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < tamano; i++) {
                // Generar fecha aleatoria dentro de un rango de 10 años desde la fecha actual
                long inicioRango = System.currentTimeMillis();
                long finRango = inicioRango - (10L * 365 * 24 * 60 * 60 * 1000); // 10 años en milisegundos
                long fechaAleatoria = inicioRango - (long) (rand.nextDouble() * (inicioRango - finRango));
                Date fecha = new Date(fechaAleatoria);

                // Generar descripción aleatoria
                String descripcion = generarDescripcionAleatoria();

                // Usar StringBuilder para mejorar el rendimiento de la concatenación
                StringBuilder linea = new StringBuilder();
                linea.append(dateFormat.format(fecha)).append(" ").append(descripcion);

                // Escribir en el archivo
                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void cargarDatosEnArbol(ArbolBST arbol, String archivo, Charset charset) {
        try {
            // Leer datos del archivo y cargar en el árbol
            Files.lines(Paths.get(archivo), charset)
                    .map(linea -> {
                        String[] partes = linea.split("\\s+", 2);
                        String fecha = partes[0];
                        String descripcion = partes[1];
                        return new Datos(fecha, descripcion);
                    })
                    .forEach(arbol::insertar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void actualizarDatoEnArbol(ArbolBST arbol) {
        Datos datoViejo = new Datos("2023-01-01", "Antigua descripción");
        Datos datoNuevo = new Datos("2023-01-01", "Nueva descripción");

        arbol.actualizarDato(datoViejo, datoNuevo);
    }  
    private static String generarDescripcionAleatoria() {
        String[] descripciones = {"Hacer ejercicio", "Reunión de trabajo", "Estudiar", "Comer", "Descansar", "Programar"};
        Random rand = new Random();
        return descripciones[rand.nextInt(descripciones.length)];
    }
    private static void eliminarDatoEnArbol(ArbolBST arbol) {
        Datos datoAEliminar = new Datos("2023-01-02", "Descripción a eliminar");

        arbol.eliminarDato(datoAEliminar);
    }
    private static void buscarDatoEnArbol(ArbolBST arbol) {
        Datos datoABuscar = new Datos("2023-01-03", "Descripción a buscar");

        Datos resultado = arbol.buscarDato(datoABuscar);
        if (resultado != null) {
            System.out.println("Dato encontrado: " + resultado);
        } else {
            System.out.println("Dato no encontrado");
        }
    }
    private static void consultarTodosLosDatosEnArbol(ArbolBST arbol) {
        int cantidadDatos = arbol.consultarTodosLosDatos();
        System.out.println("Cantidad de datos en el árbol: " + cantidadDatos);
    }
    private static void medirTiempo(String operacion, Runnable runnable) {
        long tiempoInicio = System.currentTimeMillis();
        runnable.run();
        long tiempoFin = System.currentTimeMillis();
        System.out.println(operacion + ": " + (tiempoFin - tiempoInicio) + " ms");
    }}
class Datos implements Comparable<Datos> {
    private String fecha;
    private String descripcion;
    public Datos(String fecha, String descripcion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public int compareTo(Datos otroDato) {
        // Parsear las fechas y comparar en base a la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fechaActual = dateFormat.parse(this.fecha);
            Date otraFecha = dateFormat.parse(otroDato.fecha);

            return fechaActual.compareTo(otraFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Datos{" +
                "fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
class NodoArbol {
    private Datos dato;
    private NodoArbol izquierdo;
    private NodoArbol derecho;
    public NodoArbol(Datos dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
    public Datos getDato() {
        return dato;
    }
    public void setDato(Datos dato) {
        this.dato = dato;
    }
    public NodoArbol getIzquierdo() {
        return izquierdo;
    }
    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }
    public NodoArbol getDerecho() {
        return derecho;
    }
    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }}
class ArbolBST {
    private NodoArbol raiz;
    public ArbolBST() {
        this.raiz = null;
    }
    public void insertar(Datos dato) {
        raiz = insertarRec(raiz, dato);
    }
    private NodoArbol insertarRec(NodoArbol nodo, Datos dato) {
        if (nodo == null) {
            return new NodoArbol(dato);
        }

        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), dato));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), dato));
        }

        return nodo;
    }
    public void actualizarDato(Datos datoViejo, Datos datoNuevo) {
        raiz = actualizarDatoRec(raiz, datoViejo, datoNuevo);
    }
    private NodoArbol actualizarDatoRec(NodoArbol nodo, Datos datoViejo, Datos datoNuevo) {
        if (nodo == null) {
            return null;
        }

        if (datoViejo.compareTo(nodo.getDato()) < 0) {
            nodo.setIzquierdo(actualizarDatoRec(nodo.getIzquierdo(), datoViejo, datoNuevo));
        } else if (datoViejo.compareTo(nodo.getDato()) > 0) {
            nodo.setDerecho(actualizarDatoRec(nodo.getDerecho(), datoViejo, datoNuevo));
        } else {
            // Encontramos el nodo con el dato a actualizar
            nodo.getDato().setDescripcion(datoNuevo.getDescripcion());
        }

        return nodo;
    }
    public void eliminarDato(Datos dato) {
        raiz = eliminarDatoRec(raiz, dato);
    }
    private NodoArbol eliminarDatoRec(NodoArbol nodo, Datos dato) {
        if (nodo == null) {
            return null;
        }

        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setIzquierdo(eliminarDatoRec(nodo.getIzquierdo(), dato));
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setDerecho(eliminarDatoRec(nodo.getDerecho(), dato));
        } else {
            // Encontramos el nodo con el dato a eliminar
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            } else if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            // Nodo con dos hijos, encontrar sucesor inorden
            nodo.setDato(encontrarSucesorInorden(nodo.getDerecho()));
            nodo.setDerecho(eliminarDatoRec(nodo.getDerecho(), nodo.getDato()));
        }

        return nodo;
    }
    private Datos encontrarSucesorInorden(NodoArbol nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo.getDato();
    }
    public Datos buscarDato(Datos dato) {
        return buscarDatoRec(raiz, dato);
    }
    private Datos buscarDatoRec(NodoArbol nodo, Datos dato) {
        if (nodo == null || dato.compareTo(nodo.getDato()) == 0) {
            return (nodo != null) ? nodo.getDato() : null;
        }

        if (dato.compareTo(nodo.getDato()) < 0) {
            return buscarDatoRec(nodo.getIzquierdo(), dato);
        } else {
            return buscarDatoRec(nodo.getDerecho(), dato);
        }
    }
    public int consultarTodosLosDatos() {
        return consultarTodosLosDatosRec(raiz);
    }
    private int consultarTodosLosDatosRec(NodoArbol nodo) {
        if (nodo != null) {
            int cantidadDatosIzquierdos = consultarTodosLosDatosRec(nodo.getIzquierdo());
            int cantidadDatosDerechos = consultarTodosLosDatosRec(nodo.getDerecho());
            return cantidadDatosIzquierdos + cantidadDatosDerechos + 1;
        }
        return 0;
    }
}
