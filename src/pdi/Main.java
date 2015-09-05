/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Rodrigo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        testeFiltros();
        AcessoBd con = new AcessoBd();
        con = new AcessoBd();
        con = new AcessoBd();
        con = new AcessoBd();
        con = new AcessoBd();
//        con.salvartImagem("images/imagem2.jpg");
        showImage(con.recuperarImagem("select imagem from tab1 where id = 7"));

    }

    public static void showImage(final Image img) {
        FrameView.exibirImagem((BufferedImage) img);
    }

    public static void testeFiltros() {
        try {
            //carrega nova imagem
            BufferedImage imagem1 = ImageIO.read(new File("images/imagem1.jpg"));
            //instancia um filtro e aplica a escala de cinza
            Filter filtro = new Filter();
            filtro.escalaDeCinza(imagem1);
            //gera uma nova imagem a partir da imagem1
            ImageIO.write(imagem1, "jpg", new File("images/imagem2.jpg"));

            //aqui apenas para demonstração,
            //carreguei novamente as duas imagens para exibi-las dentro de um JFrame
            imagem1 = ImageIO.read(new File("images/imagem1.jpg"));
            BufferedImage imagem2 = ImageIO.read(new File("images/imagem2.jpg"));
            FrameView show = new FrameView();
            show.exibirImagem(imagem1, imagem2);
            System.out.println("Filtro aplicado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro! Verifique se o arquivo especificado existe e tente novamente.");
        } catch (Exception e) {
            System.out.println("Erro! " + e.getMessage());
        }
    }
}
