/*
 * BUSE BOZER
 * OOP JAVA VİZE ÖDEVİ
 */

//KÜTÜPHANELER
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

/* 
BU KISIM MAİL GÖNDERMEK İÇİN KÜTÜPHANE ANCAK HATA ALIYORUM

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
*/

//Uyelerin bilgilerini tutan class
class Uye {
    private String isim;
    private String soyisim;
    private String email;

    // Constructor Metodu
    public Uye(String isim, String soyisim, String email) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.email = email;
    }

    // set-get metodları ile private değişkenlere erişim sağlıyoruz
    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

// INHERITANCE (KALITIM)
class ElitKisi extends Uye {
    public ElitKisi(String isim, String soyisim, String email) {
        super(isim, soyisim, email);
    }
}

// INHERITANCE (KALITIM)
class GenelKisi extends Uye {
    public GenelKisi(String isim, String soyisim, String email) {
        super(isim, soyisim, email);
    }
}

public class Program {
    // sınıfı kendiliğinden büyüyebilen dizi
    // hem elituyeler hem de genel uyeler için dizi oluşturuluyor
    private static ArrayList<Uye> elitUyeler = new ArrayList<Uye>();
    private static ArrayList<Uye> genelUyeler = new ArrayList<Uye>();
    // dosya adını tanımlıyoruz
    private static final String KISI_DOSYASI = "Kullanıcılar.txt";

    // elit üye ekleme
    private static void elitUyeEkle() {
        // kullanıcıdan bilgi alma
        Scanner scanner = new Scanner(System.in);

        System.out.print("Elit üyenin ismi: ");
        String isim = scanner.nextLine();

        System.out.print("Elit üyenin soyismi: ");
        String soyisim = scanner.nextLine();

        System.out.print("Elit üyenin emaili: ");
        String email = scanner.nextLine();

        // elitkisi objesi oluşturuluyor
        Uye kisi = new ElitKisi(isim, soyisim, email);
        elitUyeler.add(kisi); // oluşturulan kişi elituyeler dizisine ekleniyor

        // DOSYA YAZMA İŞLEMİ
        // Kullaıcılar.txt dosyasına kişiler sırayla ekleniyor İSİMTABSOYİSİMTABEMAİL
        try {
            FileWriter elitDosya = new FileWriter("Kullanıcılar.txt", true);
            elitDosya.write(kisi.getIsim() + "\t" + kisi.getSoyisim() + "\t" + kisi.getEmail() + "\n");
            elitDosya.close();
            System.out.println("Elit üye başarıyla eklendi.");
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    // genel üye ekleme
    private static void genelUyeEkle() {
        // kullanıcıdan bilgi alma
        Scanner scanner = new Scanner(System.in);

        System.out.print("Genel üyenin ismi: ");
        String isim = scanner.nextLine();

        System.out.print("Genel üyenin soyismi: ");
        String soyisim = scanner.nextLine();

        System.out.print("Genel üyenin emaili: ");
        String email = scanner.nextLine();

        // genelkisi objesi oluşturuluyor
        Uye kisi = new GenelKisi(isim, soyisim, email);
        genelUyeler.add(kisi); // oluşturulan kişi geneluyeler dizisine ekleniyor

        // DOSYA YAZMA İŞLEMİ
        // Kullaıcılar.txt dosyasına kişiler sırayla ekleniyor İSİMTABSOYİSİMTABEMAİL
        try {
            FileWriter genelDosya = new FileWriter("Kullanıcılar.txt", true);
            genelDosya.write(kisi.getIsim() + "\t" + kisi.getSoyisim() + "\t" + kisi.getEmail() + "\n");
            genelDosya.close();
            System.out.println("Genel üye başarıyla eklendi.");
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    // mail göndermek için olıuşturuldu
    private static void mailMenu() {
        // kullanıcıdan bilgi alınıyor
        Scanner scanner = new Scanner(System.in);

        System.out.println("1- Tüm elit üyelere mail gönder.");
        System.out.println("2- Tüm genel üyelere mail gönder.");
        System.out.println("3- Tüm üyelere mail gönder.");
        System.out.println("Seçiminiz: ");
        int mailSecim = scanner.nextInt();
        scanner.nextLine(); // Bu metot herhangi bir parametre almaz.
                            // Bu yöntem, tarayıcı nesnesinden okunan bir sonraki metin satırını döndürür.

        System.out.println("Lütfen göndermek istediğiniz maili girin:");
        String mesaj = scanner.nextLine();

        // seçime göre (1-2-3) gönderilecek kişilere mail için switch-case
        switch (mailSecim) {
            case 1:
                mailGonder(elitUyeler, mesaj);
                break;
            case 2:
                mailGonder(genelUyeler, mesaj);
                break;
            case 3:
                ArrayList<Uye> tumUyeler = new ArrayList<Uye>(elitUyeler); // tumuyelere önce elituyeler eklenerek yeni
                                                                           // bir dizi oluştruluyor
                tumUyeler.addAll(genelUyeler); // oluşan diziye genelueyeler de eklenerek tumuyeler dizimizi
                                               // oluşturuyoruz
                mailGonder(tumUyeler, mesaj);
        }
    }

    // mailmenu den gelen bilgilerle mail göndermek için oluşturuldu
    private static void mailGonder(ArrayList<Uye> gönderilecek_uyeler, String gönderilecek_mesaj) {

        // BURASI MAİL GÖNDERME KISMI ANCAK HATA ALDIĞIM VE DÜZELTEMEDİĞİM KISIM!!!!!!!

        /*
         * Scanner sc = new Scanner(System.in);
         * System.out.println("Mail gönderme işlemi başlıyor:");
         * String to = gönderilecek_uyeler.get(2);
         * //^^^burada aslında uyenın mailine ulaşmaya çalışıyorum ancak her uyenin
         * mailine
         * ulaşamıyorum dizi mantığıyla ancak hata alıyorum
         * 
         * System.out.println("Mailinizi giriniz.");
         * String from = sc.nextLine();
         * System.out.println("Şifrenizi giriniz.");
         * String yourPassword = sc.nextLine();
         * 
         * String host = "smtp.gmail.com";
         * 
         * Properties props = new Properties();
         * props.put("mail.smtp.auth", "true");
         * props.put("mail.smtp.starttls.enable", "true");
         * props.put("mail.smtp.host", host);
         * props.put("mail.smtp.port", "587");
         * 
         * Session session = Session.getInstance(props, new javax.mail.Authenticator() {
         * protected PasswordAuthentication getPasswordAuthentication() {
         * return new PasswordAuthentication(from, yourPassword);
         * }
         * });
         * 
         * try {
         * MimeMessage message = new MimeMessage(session);
         * message.setFrom(new InternetAddress(from));
         * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         * message.setSubject("MAIL GÖNDERİMİ DENEMESİ");
         * message.setText("DENEME MESAJI."); //aslında buraya gonderilecek_mesajı
         * //eklemem lazım ama çözemediğim başka bir problem
         * 
         * Transport.send(message);
         * System.out.println("E-posta gönderildi.");
         * } catch (MessagingException e) {
         * e.printStackTrace();
         * }
         */
    }

    // CLASSROOMA ATILAN DOSYA OKUMA KISMI
    public static void dosyaOku(String filepath) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filepath, Charset.forName("UTF8")));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();// Satır satır okuyor ve alttaki kod ile konsola yazdırıyor
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1- Elit üye eklemek için buraya basınız.");
            System.out.println("2- Genel üye eklemek için buraya basınız.");
            System.out.println("3- Mail göndermek için buraya basınız.");

            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();

            /*
             * burada seçim 1 ve 2 yapıldığında her seferinde dosyanın
             * yeni halini ekrana yansıtarak ekleme işleminin başarı
             * ile tamamlandığını göstermek istedim
             * o yüzden dosyaOku kısmı her ekleme sonrası bulunuyor
             */

            switch (secim) {
                case 1:
                    elitUyeEkle();
                    System.out.println("Kullanıcılar.txt dosyasına uyelerin eklenmiş hali: ");
                    dosyaOku(KISI_DOSYASI); // Kullanıcılar.txt dosyasını okur
                    break;
                case 2:
                    genelUyeEkle();
                    System.out.println("Kullanıcılar.txt dosyasına uyelerin eklenmiş hali: ");
                    dosyaOku(KISI_DOSYASI); // Kullanıcılar.txt dosyasını okur
                    break;
                case 3:
                    mailMenu();
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
                    break;
            }
            // break; koyarak program bitebilir ya da bunu koymazsak her seferinde ana ekran
            // karşımıza çıkar
        }
    }
}
