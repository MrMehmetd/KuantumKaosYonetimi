import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class KuantumCokusuException extends RuntimeException {
    public KuantumCokusuException(String message) {
        super(message);
    }
}

abstract class KuantumNesnesi {
    private final String id;
    private double stabilite;
    private int tehlikeSeviyesi;

    public KuantumNesnesi(String id, double stabilite, int tehlikeSeviyesi) {
        this.id = id;
        if (stabilite < 0 || stabilite > 100)
            throw new IllegalArgumentException("Stabilite 0-100 arasında olmalıdır.");
        this.stabilite = stabilite;
        if (tehlikeSeviyesi < 1 || tehlikeSeviyesi > 10)
            throw new IllegalArgumentException("Tehlike seviyesi 1-10 arasında olmalıdır.");
        this.tehlikeSeviyesi = tehlikeSeviyesi;
    }

    public String getId() {
        return id;
    }

    public double getStabilite() {
        return stabilite;
    }

    protected void setStabilite(double value) {
        if (value > 100) value = 100;
        if (value < 0) value = 0;
        this.stabilite = value;
        if (this.stabilite <= 0) {
            throw new KuantumCokusuException("Kuantum çöküşü! Patlayan nesne ID: " + id);
        }
    }

    public int getTehlikeSeviyesi() {
        return tehlikeSeviyesi;
    }

    protected void azaltStabilite(double miktar) {
        setStabilite(getStabilite() - miktar);
    }

    protected void arttirStabilite(double miktar) {
        setStabilite(getStabilite() + miktar);
    }

    public abstract void analizEt();

    public String durumBilgisi() {
        return "ID: " + id + ", Stabilite: " + stabilite + ", Tehlike: " + tehlikeSeviyesi;
    }
}

interface IKritik {
    void acilDurumSogutmasi();
}

class VeriPaketi extends KuantumNesnesi {
    public VeriPaketi(String id, double stabilite) {
        super(id, stabilite, 2);
    }

    @Override
    public void analizEt() {
        azaltStabilite(5);
        System.out.println("Veri içeriği okundu.");
    }
}

class KaranlikMadde extends KuantumNesnesi implements IKritik {
    public KaranlikMadde(String id, double stabilite) {
        super(id, stabilite, 7);
    }

    @Override
    public void analizEt() {
        azaltStabilite(15);
        System.out.println("Karanlık madde analiz edildi.");
    }

    @Override
    public void acilDurumSogutmasi() {
        arttirStabilite(50);
        System.out.println("Karanlık madde acil durum soğutması uygulandı.");
    }
}

class AntiMadde extends KuantumNesnesi implements IKritik {
    public AntiMadde(String id, double stabilite) {
        super(id, stabilite, 10);
    }

    @Override
    public void analizEt() {
        System.out.println("Evrenin dokusu titriyor...");
        azaltStabilite(25);
    }

    @Override
    public void acilDurumSogutmasi() {
        arttirStabilite(50);
        System.out.println("Anti madde acil durum soğutması uygulandı.");
    }
}

public class Main {
    public static void main(String[] args) {
        List<KuantumNesnesi> envanter = new ArrayList<>();
        Random rnd = new Random();
        Scanner scanner = new Scanner(System.in);
        int sayac = 1;

        try {
            while (true) {
                System.out.println();
                System.out.println("KUANTUM AMBARI KONTROL PANELİ");
                System.out.println("1. Yeni Nesne Ekle");
                System.out.println("2. Tüm Envanteri Listele");
                System.out.println("3. Nesneyi Analiz Et");
                System.out.println("4. Acil Durum Soğutması Yap");
                System.out.println("5. Çıkış");
                System.out.print("Seçiminiz: ");
                String secim = scanner.nextLine();
                System.out.println();

                switch (secim) {
                    case "1":
                        int tip = rnd.nextInt(3);
                        String id = "N" + (sayac++);
                        double stab = 60 + rnd.nextInt(41); // 60-100
                        KuantumNesnesi nesne;
                        if (tip == 0) {
                            nesne = new VeriPaketi(id, stab);
                            System.out.println("Yeni VeriPaketi eklendi. ID: " + id);
                        } else if (tip == 1) {
                            nesne = new KaranlikMadde(id, stab);
                            System.out.println("Yeni KaranlikMadde eklendi. ID: " + id);
                        } else {
                            nesne = new AntiMadde(id, stab);
                            System.out.println("Yeni AntiMadde eklendi. ID: " + id);
                        }
                        envanter.add(nesne);
                        break;

                    case "2":
                        if (envanter.isEmpty()) {
                            System.out.println("Envanter boş.");
                        } else {
                            for (KuantumNesnesi n : envanter) {
                                System.out.println(n.durumBilgisi());
                            }
                        }
                        break;

                    case "3":
                        System.out.print("Analiz edilecek nesnenin ID'si: ");
                        String idAnaliz = scanner.nextLine();
                        KuantumNesnesi bulunan = null;
                        for (KuantumNesnesi n : envanter) {
                            if (n.getId().equals(idAnaliz)) {
                                bulunan = n;
                                break;
                            }
                        }
                        if (bulunan == null) {
                            System.out.println("Bu ID ile nesne bulunamadı.");
                        } else {
                            bulunan.analizEt();
                        }
                        break;

                    case "4":
                        System.out.print("Soğutma uygulanacak nesnenin ID'si: ");
                        String idSogut = scanner.nextLine();
                        KuantumNesnesi hedef = null;
                        for (KuantumNesnesi n : envanter) {
                            if (n.getId().equals(idSogut)) {
                                hedef = n;
                                break;
                            }
                        }
                        if (hedef == null) {
                            System.out.println("Bu ID ile nesne bulunamadı.");
                        } else if (hedef instanceof IKritik) {
                            ((IKritik) hedef).acilDurumSogutmasi();
                        } else {
                            System.out.println("Bu nesne soğutulamaz!");
                        }
                        break;

                    case "5":
                        return;

                    default:
                        System.out.println("Geçersiz seçim.");
                        break;
                }
            }
        } catch (KuantumCokusuException ex) {
            System.out.println();
            System.out.println("SİSTEM ÇÖKTÜ! TAHLİYE BAŞLATILIYOR...");
            System.out.println(ex.getMessage());
        }
    }
}




