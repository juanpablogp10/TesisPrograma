// Nombre del paquete
package TCexam_Moodle.controlador.TCexam;
// Importación de librerias y clases necesarias
// paquete java io

import TCexam_Moodle.modelo.TCexam.Tema;
import TCexam_Moodle.modelo.TCexam.Token;
import TCexam_Moodle.modelo.TCexam.Topico;
import TCexam_Moodle.modelo.TCexam.AnalisisSintactico;
import TCexam_Moodle.modelo.TCexam.Pregunta;
import TCexam_Moodle.modelo.TCexam.Respuesta;
import TCexam_Moodle.modelo.TCexam.StringTokenizer;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

//bean controlador 
@ManagedBean
//Bean de sesion
@SessionScoped
public class controladorTCxam implements Serializable {

    //Verificar el tipo de archivo, para validar el tipo aceptado
    private String verificar_tipo_archivo;
    //Numero de preguntas del archivo
    private int numeroPreguntas;
    // Contenido del editor TCexam
    private String editor;
    // clase UploaddeFile de JSF para cargar los archivos
    private StringTokenizer texto;
    //Crear objeto de la clase Tema TCexam
    private Tema tema_TCexam;
    //Crear objeto de la clase Topico TCexam
    private Topico topico_TCexam;
    //Descargar archivos XML
    private StreamedContent file;
    //Presentar mensajes de conformación o verificación
    private FacesMessage mensaje;
    //Clase StringTokenizer para separar los token
    //Crear Objeto de la clase Token
    private Token token;
    //Crear Objeto de la clases AnalisisSintactico
    private AnalisisSintactico analisisSintactico;
    private AnalisisSintactico analisisSintacticoSeleccionado;
    //Lista de preguntas evaluadas
    private List<AnalisisSintactico> listaPreguntas;
    //Lista de Token
    private List<Token> listaToken;
    //Lista de cadenas de texto
    private List<String> listaArchivo;
    //Lista de preguntas sin errores
    private List<AnalisisSintactico> listaPreguntasBuenas;
    //Lista de preguntas con errores
    private List<AnalisisSintactico> listaPreguntasMalas;
    //cadena de texto con valores de preguntas
    private String concatenar;
    //cadena de texto para mostrar en el editor
    private String concatenar1;
    private String editorErrores;
    private String modal;
    private Pregunta pregunta;
    private Respuesta respuesta;
    private List<Pregunta> listaPreguntasTCexam;
    private char letra;
    private String tipoPregunta;
    private String informe;
    private String mensajeAyuda;

    // Constructor 
    public controladorTCxam() {
        letra = 'a';
        editor = "";
        tema_TCexam = new Tema();
        analisisSintacticoSeleccionado = new AnalisisSintactico();
        topico_TCexam = new Topico();
        analisisSintactico = new AnalisisSintactico();
        listaArchivo = new ArrayList<String>();
        listaPreguntas = new ArrayList<AnalisisSintactico>();
        listaToken = new ArrayList<Token>();
        listaPreguntasMalas = new ArrayList<AnalisisSintactico>();
        listaPreguntasBuenas = new ArrayList<AnalisisSintactico>();
        pregunta = new Pregunta();
        respuesta = new Respuesta();
        listaPreguntasTCexam = new ArrayList<Pregunta>();

    }

    public void iniciarObjetos() {
        analisisSintactico = new AnalisisSintactico();
        listaArchivo = new ArrayList<String>();
        listaPreguntas = new ArrayList<AnalisisSintactico>();
        listaToken = new ArrayList<Token>();

    }

//Metodo que verifica el tipo de archivo que carga el usuario
    public boolean verificarTipoArchivo() {
        boolean verificador = false;
        //Comparar el tipo de archivo y verificar antes de cargar el arvhivo
        verificar_tipo_archivo = "";
        // Si el metodo retorna "verdadero" el tipo de archivo es aceptado por la aplicacion 
        if (".doc".contains(verificar_tipo_archivo)) {
            verificador = true;
        } else if (verificar_tipo_archivo.contains(".txt")) {
            verificador = true;
        } else if (".txt".contains(verificar_tipo_archivo)) {
            verificador = true;
        } else if (".odt".contains(verificar_tipo_archivo)) {
            verificador = true;
        } else if (".csv".contains(verificar_tipo_archivo)) {
            verificador = true;
        }
        return verificador;
    }
//Metodo para descomponer el tipo de archivo

    public String descomponerTipo(String tipo) {
        String descomponer = "";
        int pos = 0;
        //Realizar un recorrido hasta buscar el caracter .
        //y guardar la posicion del caracter 
        for (int i = 0; i < tipo.length(); i++) {
            if (tipo.charAt(i) == '.') {
                pos = i;
            }
        }
        //Realizar el recorrido desde la posicion del caracter del punto 
        //Gurdar la concatenacion de caracteres desde esa posiscion
        for (int i = pos; i < tipo.length(); i++) {
            descomponer = descomponer + tipo.charAt(i);
        }
        return descomponer;
    }

    //verificar que el archivo este seleccionado y listo para cargar 
    //el archivo en el editor
    public boolean archivo_seleccionado() {

        return true;
    }

    //Metodo para leer archivos txt
    public void leer_archivo_txt() {

    }

    //mensaje ayuda al usuario 
    public void mensajeAyuda() {
        mensajeAyuda = "";
        if (analisisSintacticoSeleccionado.getEstado().equals("Error Tipo Pregunta")) {
            mensajeAyuda = "Tipo de pregunta no identificado, revise formato para identificar preguntas";
        } else {
            mensajeAyuda = "Complete la pregunta <identificador= 1. o a.> <texto=texto pregunta o respuesta>   ";
        }
    }

    public void editordeError() {
        mensajeAyuda();
        editorErrores = "";
        String aux = "";
        boolean bandera = false;
        for (Token token1 : analisisSintacticoSeleccionado.getListaToken()) {
            if ("letras".equals(token1.getTipo()) | "numerico".equals(token1.getTipo())) {
                if (bandera == true) {
                    editorErrores = editorErrores + verificarNumero(aux);
                    aux = "";
                }
                bandera = true;
                aux = aux + token1.getToken();
            } else {
                bandera = false;
                aux = aux + " " + token1.getToken();
                editorErrores = editorErrores + verificarNumero(aux);
                aux = "";
            }
        }
    }

    //Metodo para verificar que la linea empieze con numero
    public String verificarNumero(String palabra) {
        if (Character.isDigit(palabra.charAt(0)) == false) {
            return "\t" + palabra + "\n";
        } else {
            return palabra + "\n";
        }
    }

    //metodo para asignar tema, topico, descripción
    public String asignar_tema_examen_TCexam() {
        return "<center><h2>Evaluación TCexam </h2></center>"
                + "<b>Tema : </b>" + tema_TCexam.getTema()
                + "&nbsp" + "<b>Topico : </b>" + topico_TCexam.getTopico() + "<br/>"
                + "<b>Descripción : </b>" + tema_TCexam.getDescripcion() + "<br/>"
                + "<h4>Banco de Preguntas </h4>";
    }

    //Método para divir el documento en tokens y almacenar en una lista 
//Para analizar el texto
    public void limpiarHtmlEditor() {
        System.out.println("EITOR =" + editor);
        int num = 0;
        concatenar = editor;
        concatenar = concatenar.replaceAll("\t", " ");
        concatenar = concatenar.replaceAll("\n", " ");
        System.out.println("CONCATENAR " + concatenar);
    }

    public void dividirTextoToken() {
        iniciarObjetos();
        limpiarHtmlEditor();
        // clase StringTokenizer de java para dividir en cadenas un texto
//        concatenar = concatenar.trim();
//        for (int i = 0; i < concatenar.length(); i++) {
//            if (concatenar.charAt(i) == ' ' & !"".equals(nuevo)) {
//                listaArchivo.add(nuevo);
//                nuevo = "";
//            } else if (concatenar.charAt(i) != ' ') {
//                nuevo = nuevo + concatenar.charAt(i);
//            }
//        }

        texto = new StringTokenizer(concatenar);
        // Almacenar Token en una lista
        while (texto.hasMoreTokens()) {
            //Asignado ala lista el token      
            listaArchivo.add(texto.nextToken());
        }

    }
//Método para analaizar los Token y a agruparlos en los Token analizados en la applicación  

    public void AgruparTokenA() {
//        limpiarCadenas();
        //variable para concatenar el token TextoPregunta o TextoRespuesta
        String tokenAux = "";
        String tok = "";
        //recorrer la lista de los token para analizar 
        for (int i = 0; i < listaArchivo.size(); i++) {
            //identificando token numerico
            tok = listaArchivo.get(i).replaceAll(" ", "");
//            System.out.println("-" + listaArchivo.get(i) + "-");

            if (identificarToken(tok).equals("numerico")) {
                //Agregar el token texto
                this.almacenarTexto(tokenAux);
                tokenAux = "";
                //asignando valores al Token numerico
                token = new Token(tok, "numerico", "correcto");
                //asignar token a lista de tokens 
                listaToken.add(token);
                //identificando token numeración Letras 
            } else if (identificarToken(tok).equals("letras")) {
                //Agregar el token texto
                this.almacenarTexto(tokenAux);
                tokenAux = "";
                //asignado valores a token numeración letras 
                token = new Token(tok, "letras", "correcto");
                //asignar token a lista de tokens 
                listaToken.add(token);
            } else {
                //almacenando el texto de la pregunta o respuesta  
                tokenAux = tokenAux + tok + " ";
                System.out.println(tokenAux);
            }
        }
        this.almacenarTexto(tokenAux);
    }

    //Método para identificar el tipo de token 
    // 1. token numero 2. token numeracion letras
    // 3. token texto
    public String identificarToken(String tokenA) {
        //Variable para asignar tipo de token 
        String tipoToken = "";
        //Para identificar el tipo de token verificamos 
        // cual contiene punto.
        if (tokenA.charAt(tokenA.length() - 1) == '.' | tokenA.charAt(tokenA.length() - 1) == ')') {
            tipoToken = tokenNumeroLetra(tokenA);
        } else {
            tipoToken = "texto";
        }
        return tipoToken;
    }
// Método para almacenar valor al token texto

    public void almacenarTexto(String textoV) {
        //verificar si la variable contiene valor para almacenar

        if (!"".equals(textoV)) {
            //crear el token y asignar valores 
            token = new Token(textoV, "texto", "correcto");
            //Agregar ala lista
            listaToken.add(token);

        }

    }

    public String tokenNumeroLetra(String tokenVa) {
        String tp = null;
        int estado = 1;
        for (int i = 0; i < tokenVa.length(); i++) {
            estado = verifigarEstado(estado, tokenVa.charAt(i));
        }
        if (estado == 2) {
            tp = "numerico";
        } else if (estado == 4) {
            tp = "letras";
        } else {
            tp = "texto";
        }
        return tp;
    }

    public int verifigarEstado(int estado, char simbolo) {
        int numero = 0;
        if (estado == 1) {
            if (simbolo == '0' | simbolo == '1' | simbolo == '2' | simbolo == '3' | simbolo == '4'
                    | simbolo == '5' | simbolo == '6' | simbolo == '7' | simbolo == '8' | simbolo == '9') {

                numero = 1;
            }
        }
        if (estado == 1) {
            if (simbolo == 'a' | simbolo == 'b' | simbolo == 'c' | simbolo == 'd' | simbolo == 'e'
                    | simbolo == 'f' | simbolo == 'g' | simbolo == 'h' | simbolo == 'i' | simbolo == 'j' | simbolo == 'k'
                    | simbolo == 'l' | simbolo == 'm' | simbolo == 'n' | simbolo == 'o' | simbolo == 'p' | simbolo == 'q' | simbolo == 'r') {

                numero = 3;
            }
        }
        if (estado == 1) {
            if (simbolo == '.' | simbolo == ')') {
                numero = 2;

            }
        }

        if (estado == 3) {
            if (simbolo == '.' | simbolo == ')') {
                numero = 4;

            }
        }
        if (estado >= 5) {
            numero = 5;

        }

        return numero;
    }

    public void AgruparEstructuraPregunta() {
        int i = 0;
        for (Token toke : listaToken) {
            if ("numerico".equals(toke.getTipo()) && i != 0) {
                listaPreguntas.add(analisisSintactico);
                analisisSintactico = new AnalisisSintactico();
                analisisSintactico.getListaToken().add(toke);
                i++;

            } else {
                analisisSintactico.getListaToken().add(toke);
                i++;
            }
        }
        listaPreguntas.add(analisisSintactico);
    }

    public boolean verificarEstructuraArreglo(int[] num) {
        boolean bandera = true;
        int var = 2;
        if (num[0] == 0 && num.length % 2 == 0) {
            for (int i = 1; i < num.length; i++) {
                if (num[i] != var) {
                    bandera = false;
                }
                if (i % 2 == 0) {
                    var = 2;
                } else {
                    var = 1;
                }
            }
        } else {
            bandera = false;
        }

        return bandera;
    }

    public boolean verificarEstructuratoken(List<Token> lista) {
        int cont = 0;
        int[] num = new int[lista.size()];
        for (Token token1 : lista) {
            if ("numerico".equals(token1.getTipo())) {
                num[cont] = 0;
                cont++;
            }
            if ("letras".equals(token1.getTipo())) {
                num[cont] = 1;
                cont++;

            }
            if ("texto".equals(token1.getTipo())) {
                num[cont] = 2;
                cont++;
            }
        }
        return verificarEstructuraArreglo(num);

    }

    public int verificarTipoPreguntaTcexam(List<Token> lista) {
        String verificar = "";
        int asteriscosRes = 0;
        for (Token res : lista) {
            verificar = res.getToken();
            verificar = verificar.replaceAll(" ", "");
            verificar = verificar.trim();
            System.out.println("Verifovar METPDP ERROR" + verificar);
            if (verificar.charAt(verificar.length() - 1) == '*') {
                asteriscosRes = asteriscosRes + 1;
            }
        }
        return asteriscosRes;
    }

    public String verificarTipoPreguntaTXexam(List<Token> lista) {
        List<Token> listare = new ArrayList<Token>();
        String tipo = "";
        String ver = "";
        if (lista.size() == 2) {
            tipo = "ensayo";
        } else {
            for (Token token1 : lista) {
                if ("texto".equals(token1.getTipo())) {
                    listare.add(token1);
                }
            }
            ver = listare.get(0).getToken();
            ver = ver.replaceAll(" ", "");
            ver = ver.trim();
            if (ver.charAt(ver.length() - 1) == '*') {
                tipo = "Ordenar";
            } else {
                listare.remove(0);
                if (verificarTipoPreguntaTcexam(listare) == 1) {
                    tipo = "Simple";
                } else if (verificarTipoPreguntaTcexam(listare) >= 2) {
                    tipo = "Multiple";

                } else {
                    tipo = "error";
                }
            }

        }

        return tipo;
    }

    public void verificarEstructuraPregunta() {
        listaPreguntasMalas = new ArrayList<AnalisisSintactico>();
        listaPreguntasBuenas = new ArrayList<AnalisisSintactico>();
        listaPreguntasTCexam = new ArrayList<Pregunta>();

//        listaPreguntasMalas.clear();
        int id = 1;
        dividirTextoToken();
        AgruparTokenA();
        AgruparEstructuraPregunta();
        for (AnalisisSintactico analisisSintactico1 : listaPreguntas) {
            if (verificarEstructuratoken(analisisSintactico1.getListaToken())) {
                if (!"error".equals(verificarTipoPreguntaTXexam(analisisSintactico1.getListaToken()))) {
                    pregunta.setIdentificador(id + ".");
                    pregunta.setTipo(verificarTipoPreguntaTXexam(analisisSintactico1.getListaToken()));
                    pregunta.setPregunta(analisisSintactico1.getListaToken().get(1).getToken());
                    int par = 1;
                    for (int i = 2; i < analisisSintactico1.getListaToken().size(); i++) {
                        if (par % 2 == 0) {
                            respuesta.setRespuesta(analisisSintactico1.getListaToken().get(i).getToken());
                            pregunta.getListaRespuesta().add(respuesta);
                            respuesta = new Respuesta();
                        } else {
                            respuesta.setIdentificador(analisisSintactico1.getListaToken().get(i).getToken());
                        }
                        par++;
                    }
                    listaPreguntasTCexam.add(pregunta);
                    pregunta = new Pregunta();

                } else {
                    analisisSintactico1.setId(id);
                    analisisSintactico1.setEstado("Error Tipo Pregunta");
                    listaPreguntasMalas.add(analisisSintactico1);
                    id = id + 1;
                }

            } else {
                analisisSintactico1.setId(id);
                analisisSintactico1.setEstado("Error Sintaxis Pregunta");
                listaPreguntasMalas.add(analisisSintactico1);
                id = id + 1;
            }
        }
        numeroPreguntas = listaPreguntas.size();
    }

    public void corregirErrores() {
        mensajeAyuda();
        editor = editorErrores;
        int id = analisisSintacticoSeleccionado.getId();
        int incremento = 1;
        int cerrarModal = 1;
        dividirTextoToken();
        AgruparTokenA();
        AgruparEstructuraPregunta();
        for (AnalisisSintactico analisisSintactico1 : listaPreguntas) {
            if (verificarEstructuratoken(analisisSintactico1.getListaToken())) {
                if (!"error".equals(verificarTipoPreguntaTXexam(analisisSintactico1.getListaToken()))) {

                    if (listaPreguntas.size() == 1) {

                        pregunta.setIdentificador(id + ".");
                        pregunta.setTipo(verificarTipoPreguntaTXexam(analisisSintactico1.getListaToken()));
                        pregunta.setPregunta(analisisSintactico1.getListaToken().get(1).getToken());
                        int par = 1;
                        for (int i = 2; i < analisisSintactico1.getListaToken().size(); i++) {
                            if (par % 2 == 0) {
                                respuesta.setRespuesta(analisisSintactico1.getListaToken().get(i).getToken());
                                pregunta.getListaRespuesta().add(respuesta);
                                respuesta = new Respuesta();
                            } else {
                                respuesta.setIdentificador(analisisSintactico1.getListaToken().get(i).getToken());

                            }

                            par++;
                        }
                        listaPreguntasTCexam.add(pregunta);
                        pregunta = new Pregunta();

                        listaPreguntasMalas.remove(analisisSintacticoSeleccionado);
                        actualizarEditor();
                        cerrar();
                    } else {
                        pregunta.setIdentificador(id + ".");
                        pregunta.setTipo(verificarTipoPreguntaTXexam(analisisSintactico1.getListaToken()));
                        pregunta.setPregunta(analisisSintactico1.getListaToken().get(1).getToken());
                        int par = 1;
                        for (int i = 2; i < analisisSintactico1.getListaToken().size(); i++) {
                            if (par % 2 == 0) {
                                respuesta.setRespuesta(analisisSintactico1.getListaToken().get(i).getToken());
                                pregunta.getListaRespuesta().add(respuesta);
                                respuesta = new Respuesta();
                            } else {
                                respuesta.setIdentificador(analisisSintactico1.getListaToken().get(i).getToken());
                            }
                            par++;
                        }
                        listaPreguntasTCexam.add(pregunta);
                        pregunta = new Pregunta();
                        listaPreguntasMalas.remove(analisisSintacticoSeleccionado);
                        id = numeroPreguntas + incremento;
                        incremento = incremento + 1;
                        cerrarModal = cerrarModal + 1;
                        if (cerrarModal == listaPreguntas.size()) {
                            actualizarEditor();
                            cerrar();
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error tipo pregunta, no corresponde a los tipos de pregunta"));

                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error de sintaxis complete la pregunta <identificador><texto>"));
            }
        }

    }

//    public void actualizarEditor() {
//        editor = "";
//        for (Pregunta pre : listaPreguntasTCexam) {
//            String aux = "";
//            boolean bandera = false;
//            for (Token token1 : pre.getListaToken()) {
//                if ("letras".equals(token1.getTipo()) | "numerico".equals(token1.getTipo())) {
//                    if (bandera == true) {
//                        editor = editor + verificarNumero(aux);
//                        System.out.println("editor actualizado = " + editor);
//                        aux = "";
//                    }
//                    bandera = true;
//                    aux = aux + token1.getToken();
//                } else {
//                    bandera = false;
//                    aux = aux + " " + token1.getToken();
//                    editor = editor + verificarNumero(aux);
//                    aux = "";
//                }
//            }
//
//        }
//
//    }
    public void actualizarEditor() {
        String valor = "";
        editor = "";
        for (Pregunta pre : listaPreguntasTCexam) {
            valor = pre.getIdentificador() + " " + pre.getPregunta();
            editor = editor + verificarNumero(valor);
            for (Respuesta respues : pre.getListaRespuesta()) {
                valor = respues.getIdentificador() + " " + respues.getRespuesta();
                editor = editor + verificarNumero(valor);
            }
        }
    }

    public String AgregarInforme(String palabra) {
        if (Character.isDigit(palabra.charAt(0)) == false) {
            return "   " + palabra + "<br/>";
        } else {
            return "<b>" + palabra + "</b>" + "<br/>";
        }
    }

    public void ingresarDatosInforme() {
        informe = asignar_tema_examen_TCexam();
        String valor = "";

        for (Pregunta pre : listaPreguntasTCexam) {
            valor = pre.getIdentificador() + " " + pre.getPregunta();
            informe = informe + AgregarInforme(valor);
            for (Respuesta respues : pre.getListaRespuesta()) {
                valor = respues.getIdentificador() + " " + respues.getRespuesta();
                informe = informe + AgregarInforme(valor);
            }
        }
    }

    //Metodo para generar informe PDF
    public void generarPDF() {
        if (editor.length() != 0) {
            if (listaPreguntasTCexam.size() != 0) {
                ingresarDatosInforme();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg10').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Verificara Preguntas Correctas para obtener informe"));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Editor Vacio"));

        }
    }

    //Metodo para verificar numero de preguntas y errores en preguntas 
    public void errores_preguntas1() {
        if (editor.length() != 0) {
            verificarEstructuraPregunta();
            if (0 != listaPreguntasMalas.size()) {
                System.out.println("entro1");
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg2').show();");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg3').show();");

            }
        } else {
            System.out.println("entro3");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Editor Vacio"));
        }

        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Editor Vacio"));
    }

    public void cerrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg7').hide();");

    }

    public void borrar() {
        modal = " ";
        if (analisisSintacticoSeleccionado.getId() == 4) {
            listaPreguntasMalas.remove(analisisSintacticoSeleccionado);
            cerrar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Error sintactico complete la pregunta"));
        }
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Error sintactico complete la pregunta"));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "No ha marcado la respuesta"));

//        return "PF('dlg7').hide();";
    }

    //metodos de navegación 
    public String ir_tcexam() {
        return "TCxamp?faces-redirect=true";
    }

    public String ir_pru() {
        return "index?faces-redirect=true";
    }

    //Metodo para descargar los archivos XML
    public void descargar_archivosXML() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/archivosXml/anteproyecto.xml");
        file = new DefaultStreamedContent(stream, "application/xml", "anteproyecto.xml");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg3').hide();");
    }

    // AGREGAR PREGUNTAS TECXAM
    //Agregar tipo respuesta Simple
    //Agregar Respuesta
    public void abrirAgregarpregunta() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg6').show();");
    }

    public void eliminarRespuesta() {
        pregunta.getListaRespuesta().remove(respuesta);
        respuesta = new Respuesta();
    }

    public void agregarPreguntasEnsayo() {
        pregunta.setTipo("ensayo");
        pregunta.setIdentificador("9.");
        listaPreguntasTCexam.add(pregunta);
        pregunta = new Pregunta();
    }

    public void agregarRespuestaSimple() {
        //Editar
        if (pregunta.getListaRespuesta().contains(respuesta)) {
            respuesta.setRespuesta(respuesta.getRespuesta().trim());
            respuesta.setRespuesta(respuesta.getRespuesta().replaceAll(" ", ""));
            if (respuesta.getRespuesta().charAt(respuesta.getRespuesta().length() - 1) == '*') {
                respuesta.setEstado("*");
            }
            respuesta = new Respuesta();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg6').hide();");
            //Agragar 
        } else {

            String id = letra + ".";
            respuesta.setRespuesta(respuesta.getRespuesta().trim());
            respuesta.setRespuesta(respuesta.getRespuesta().replaceAll(" ", ""));
            if (respuesta.getRespuesta().charAt(respuesta.getRespuesta().length() - 1) == '*') {
                respuesta.setEstado("*");
            }
            respuesta.setIdentificador(id);
            pregunta.getListaRespuesta().add(respuesta);
            respuesta = new Respuesta();
            letra++;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg6').hide();");

        }
    }

    public int verificarTipoPregunta() {
        String verificar = "";
        int asteriscosRes = 0;
        for (Respuesta res : pregunta.getListaRespuesta()) {
            verificar = res.getRespuesta();
            verificar = verificar.replaceAll(" ", "");
            verificar = verificar.trim();
            if (verificar.charAt(verificar.length() - 1) == '*') {
                asteriscosRes = asteriscosRes + 1;
            }
        }
        return asteriscosRes;
    }

    public void agregarPreguntaSimpleTCexam() {
        int incremeto = 1;
        if ("Preguntas Selección Simple".equals(tipoPregunta)) {
            if (verificarTipoPregunta() == 1) {
                pregunta.setIdentificador(listaPreguntasBuenas.size() + ".");
                pregunta.setTipo("simple");
                listaPreguntasTCexam.add(pregunta);
                pregunta = new Pregunta();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg4').hide();");
            } else if (verificarTipoPregunta() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "No se ha colocado asterisco(*) en la respuesta correcta"));

            } else if (verificarTipoPregunta() >= 2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", " El caracter asterisco solo debe estar una vez "));
            }
        } else if ("Preguntas Selección Multiple".equals(tipoPregunta)) {
            if (verificarTipoPregunta() == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", " El caracter asterisco solo esta una vez, no corresponde tipo pregunta "));

            } else if (verificarTipoPregunta() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "No se ha colocado asteriscos(*) en la respuesta correcta"));

            } else if (verificarTipoPregunta() >= 2) {
                pregunta.setIdentificador(listaPreguntasBuenas.size() + ".");
                pregunta.setTipo("multiple");
                listaPreguntasTCexam.add(pregunta);
                pregunta = new Pregunta();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg4').hide();");
            }

        } else if ("Preguntas Ordenar Respuesta".equals(tipoPregunta)) {
            if (verificarTipoPregunta() == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", " No debe contener asteriscos"));

            } else if (verificarTipoPregunta() == 0) {
                pregunta.setIdentificador(listaPreguntasBuenas.size() + ".");
                pregunta.setTipo("ordenar");
                listaPreguntasTCexam.add(pregunta);
                pregunta = new Pregunta();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlg4').hide();");

            } else if (verificarTipoPregunta() >= 2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "No debe contener asteriscos"));

            }

        }

    }

    public void llamarSimple() {
        letra = 'a';
        tipoPregunta = "Preguntas Selección Simple";
    }

    public void llamaMultiple() {
        letra = 'a';
        tipoPregunta = "Preguntas Selección Multiple";
    }

    public void llamarOrdenar() {
        letra = 'a';
        tipoPregunta = "Preguntas Ordenar Respuesta";
    }

    // Métodos Accesores 
    public String getVerificar_tipo_archivo() {
        return verificar_tipo_archivo;
    }

    public void setVerificar_tipo_archivo(String verificar_tipo_archivo) {
        this.verificar_tipo_archivo = verificar_tipo_archivo;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Tema getTema_TCexam() {
        return tema_TCexam;
    }

    public void setTema_TCexam(Tema tema_TCexam) {
        this.tema_TCexam = tema_TCexam;
    }

    public Topico getTopico_TCexam() {
        return topico_TCexam;
    }

    public void setTopico_TCexam(Topico topico_TCexam) {
        this.topico_TCexam = topico_TCexam;
    }

    public int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(int numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public FacesMessage getMensaje() {
        return mensaje;
    }

    public void setMensaje(FacesMessage mensaje) {
        this.mensaje = mensaje;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AnalisisSintactico getAnalisisSintactico() {
        return analisisSintactico;
    }

    public void setAnalisisSintactico(AnalisisSintactico analisisSintactico) {
        this.analisisSintactico = analisisSintactico;
    }

    public List<AnalisisSintactico> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<AnalisisSintactico> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public List<Token> getListaToken() {
        return listaToken;
    }

    public void setListaToken(List<Token> listaToken) {
        this.listaToken = listaToken;
    }

    public List<String> getListaArchivo() {
        return listaArchivo;
    }

    public void setListaArchivo(List<String> listaArchivo) {
        this.listaArchivo = listaArchivo;
    }

    public List<AnalisisSintactico> getListaPreguntasBuenas() {
        return listaPreguntasBuenas;
    }

    public void setListaPreguntasBuenas(List<AnalisisSintactico> listaPreguntasBuenas) {
        this.listaPreguntasBuenas = listaPreguntasBuenas;
    }

    public List<AnalisisSintactico> getListaPreguntasMalas() {
        return listaPreguntasMalas;
    }

    public void setListaPreguntasMalas(List<AnalisisSintactico> listaPreguntasMalas) {
        this.listaPreguntasMalas = listaPreguntasMalas;
    }

    public String getConcatenar() {
        return concatenar;
    }

    public void setConcatenar(String concatenar) {
        this.concatenar = concatenar;
    }

    public String getConcatenar1() {
        return concatenar1;
    }

    public void setConcatenar1(String concatenar1) {
        this.concatenar1 = concatenar1;
    }

    public String getEditorErrores() {
        return editorErrores;
    }

    public void setEditorErrores(String editorErrores) {
        this.editorErrores = editorErrores;
    }

    public AnalisisSintactico getAnalisisSintacticoSeleccionado() {
        return analisisSintacticoSeleccionado;
    }

    public void setAnalisisSintacticoSeleccionado(AnalisisSintactico analisisSintacticoSeleccionado) {
        this.analisisSintacticoSeleccionado = analisisSintacticoSeleccionado;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public List<Pregunta> getListaPreguntasTCexam() {
        return listaPreguntasTCexam;
    }

    public void setListaPreguntasTCexam(List<Pregunta> listaPreguntasTCexam) {
        this.listaPreguntasTCexam = listaPreguntasTCexam;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public StringTokenizer getTexto() {
        return texto;
    }

    public void setTexto(StringTokenizer texto) {
        this.texto = texto;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public String getMensajeAyuda() {
        return mensajeAyuda;
    }

    public void setMensajeAyuda(String mensajeAyuda) {
        this.mensajeAyuda = mensajeAyuda;
    }

}
