package com.example;

import com.example.paquete.Paquete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HelloController implements Runnable{

    public int PUERTO_MIDDLEWARE = 11000;
    int numCarpetas[] = {13000, 13001, 13002, 13003, 13004, 13005, 13006, 13007};
    public Paquete paquete = new Paquete("",  0, '0','0', " ", " ");

    @FXML
    private TextField IdDiv;

    @FXML
    private TextField IdMulti;

    @FXML
    private TextField IdResta;

    @FXML
    private TextField IdRutaJar;

    @FXML
    private TextField IdSuma;


    @FXML
    void ckActualizar(ActionEvent event) { //Mando la actualización de los acuses mínimos de cada operación a los un Nodo Disponíble
        int mandar = 0;
        while (mandar<4)
        {
            try {
                Socket enviaReceptor=new Socket("127.0.0.1", PUERTO_MIDDLEWARE);
                ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaReceptor.getOutputStream());
                paquete.setIDdireccion('A');
                paquete.setCodigoOperacion('h');


                //actualizo los acuses mínimos
                String sm = IdSuma.getText();
                int suma=Integer.parseInt(sm);

                String rs = IdResta.getText();
                int resta=Integer.parseInt(rs);

                String m = IdMulti.getText();
                int mult=Integer.parseInt(m);


                String d = IdDiv.getText();
                int div=Integer.parseInt(d);




                paquete.setAcusesActualizadosSuma(suma);
                paquete.setAcusesActualizadosResta(resta);
                paquete.setAcusesActualizadosMultiplicacion(mult);
                paquete.setAcusesActualizadosDivision(div);
                paqueteReenvio.writeObject(paquete);
                paqueteReenvio.close();
                enviaReceptor.close();
                mandar=5;
                break;

            }catch (IOException e){
                PUERTO_MIDDLEWARE++;
            }
        }
    }


    @FXML
    void ckMicroCerviciosJar(ActionEvent event) throws IOException {
        String tipoOperacion = IdRutaJar.getText();
        switch (tipoOperacion){
            case "suma":
                copiaSum();
                break;
            case "resta":
                copiaRes();
                break;
            case "multiplicacion":
                copiaM();
                break;
            case "division":
                copiaD();
                break;
        }
    }

    //copio ooperación al resto de mis carpetas dependiendo de la operación que quiera
    public void copiaSum() throws IOException {
        FileWriter file = new FileWriter("C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\batEnviaServicios\\enviaServ.bat");

        for (int num : numCarpetas){
            String temp = "copy C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\Operaciones\\suma\\suma.jar  C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\"+num;
            System.out.println(num);
            new ProcessBuilder("cmd.exe", "/c", temp).start();

        }

    }

    public void copiaRes() throws IOException{
        FileWriter file = new FileWriter("C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\batEnviaServicios\\enviaServ.bat");

        for (int num : numCarpetas){
            String temp = "copy C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\Operaciones\\resta\\resta.jar  C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\"+num;
            System.out.println(num);
            new ProcessBuilder("cmd.exe", "/c", temp).start();
        }
    }
    public void copiaM() throws IOException{
        FileWriter file = new FileWriter("C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\batEnviaServicios\\enviaServ.bat");

        for (int num : numCarpetas){
            String temp = "copy C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\Operaciones\\multi\\multi.jar  C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\"+num;
            System.out.println(num);
            new ProcessBuilder("cmd.exe", "/c", temp).start();
        }
    }
    public void copiaD() throws IOException{
        FileWriter file = new FileWriter("C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\batEnviaServicios\\enviaServ.bat");

        for (int num : numCarpetas){
            String temp = "copy C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\Operaciones\\div\\div.jar  C:\\Users\\pined\\OneDrive\\Escritorio\\MicroOp\\"+num;
            System.out.println(num);
            new ProcessBuilder("cmd.exe", "/c", temp).start();
        }
    }

    @Override
    public void run() {

    }
}
