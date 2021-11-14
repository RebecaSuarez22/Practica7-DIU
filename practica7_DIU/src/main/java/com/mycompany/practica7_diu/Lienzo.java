package com.mycompany.practica7_diu;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;



public class Lienzo extends JPanel{
    private BufferedImage imagen= null;
    public String fichero = "";     
    Mat mat;
    private int maximo[] = new int[3];
    private int minimo[] = new int[3];
    private int promedio[] = new int[3];
    
    public Lienzo(){        
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        try {
            imagen = ImageIO.read(new URL("https://www.purina-latam.com/sites/g/files/auxxlc391/files/styles/social_share_large/public/purina-reconocer-a-un-perro-feliz.jpg?itok=8xw_Cyof"));
          
        } catch (MalformedURLException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public int abrirImagen(String fichero){
        
        //Abrimos la imagen 
        this.fichero = fichero;
        try {
            imagen = ImageIO.read(new File(fichero));
            
        } catch (IOException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }        
                        
        return 0;
    }
    
    public int[] getMaximo() {
        return maximo;
    }

    public int[] getMinimo() {
        return minimo;
    }

    public int[] getPromedio() {
        return promedio;
    }
    
    public void coordenadas(int x, int y, int width, int height){
        
        EstadisticasImagen estadisticas = new EstadisticasImagen();  
        
        Point p = new Point(x, y);        
        Dimension d = new Dimension(width, height);
        
        mat = new Mat(imagen.getHeight(), imagen.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) imagen.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data); 
        
        estadisticas.calculaEstadisticas(mat, p, d);
        
        maximo = estadisticas.getMaximo();
        minimo = estadisticas.getMinimo();
        promedio = estadisticas.getPromedio();       
         
    }
    
     public void guardarImagen(File fichero){
        try {
            ImageIO.write(imagen, "jpg", fichero);
        } catch (IOException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
   
    
    @Override
    public void paintComponent(Graphics g){
        this.setPreferredSize(new Dimension(imagen.getWidth(),imagen.getHeight()));
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, null); 
        repaint(); 
        
    }
}
