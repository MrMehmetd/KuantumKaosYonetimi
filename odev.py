import random
import sys


class KuantumCokusuException(Exception):
    def __init__(self, nesne_id: str):
        super().__init__(f"Kuantum Çöküşü! Patlayan nesne ID: {nesne_id}")
        self.nesne_id = nesne_id


class KuantumNesnesi:
    def __init__(self, id_: str, stabilite: float, tehlike_seviyesi: int):
        self._id = id_
        self._stabilite = 0.0
        self.id = id_
        self.tehlike_seviyesi = max(1, min(10, tehlike_seviyesi))
        self.stabilite = stabilite

    @property
    def id(self):
        return self._id

    @id.setter
    def id(self, value):
        self._id = value

    @property
    def stabilite(self) -> float:
        return self._stabilite

    @stabilite.setter
    def stabilite(self, value: float):
        if value > 100:
            value = 100
        if value < 0:
            value = 0
        self._stabilite = value

    def stabilite_degistir(self, delta: float):
        self.stabilite = self.stabilite + delta
        if self.stabilite <= 0:
            raise KuantumCokusuException(self.id)

    def analiz_et(self):
        raise NotImplementedError

    def durum_bilgisi(self) -> str:
        return f"ID: {self.id}, Stabilite: {self.stabilite:.2f}, Tehlike: {self.tehlike_seviyesi}"


class VeriPaketi(KuantumNesnesi):
    def analiz_et(self):
        print("Veri içeriği okundu.")
        self.stabilite_degistir(-5)


class KaranlikMadde(KuantumNesnesi):
    def analiz_et(self):
        print("Karanlık madde titreşimi analiz ediliyor...")
        self.stabilite_degistir(-15)

    def acil_durum_sogutmasi(self):
        print("Karanlık madde için acil soğutma uygulanıyor...")
        self.stabilite_degistir(+50)


class AntiMadde(KuantumNesnesi):
    def analiz_et(self):
        print("Evrenin dokusu titriyor...")
        self.stabilite_degistir(-25)

    def acil_durum_sogutmasi(self):
        print("Anti madde için ACİL soğutma uygulanıyor!")
        self.stabilite_degistir(+50)


def yeni_nesne_ekle(envanter):
    tip = random.randint(0, 2)
    id_ = f"N{len(envanter) + 1}"
    stabilite = random.randint(50, 100)
    tehlike = random.randint(1, 10)

    if tip == 0:
        nesne = VeriPaketi(id_, stabilite, tehlike)
        print(f"VeriPaketi oluşturuldu. ID: {id_}")
    elif tip == 1:
        nesne = KaranlikMadde(id_, stabilite, tehlike)
        print(f"KaranlikMadde oluşturuldu. ID: {id_}")
    else:
        nesne = AntiMadde(id_, stabilite, tehlike)
        print(f"AntiMadde oluşturuldu. ID: {id_}")

    envanter.append(nesne)


def envanteri_listele(envanter):
    if not envanter:
        print("Envanter boş.")
        return
    for n in envanter:
        print(n.durum_bilgisi())


def nesne_bul(envanter, id_):
    for n in envanter:
        if n.id == id_:
            return n
    return None


def nesne_analiz_et(envanter):
    id_ = input("Analiz edilecek nesnenin ID'si: ").strip()
    nesne = nesne_bul(envanter, id_)
    if nesne is None:
        print("Nesne bulunamadı.")
        return
    nesne.analiz_et()
    print("Yeni durum:", nesne.durum_bilgisi())


def sogutma_yap(envanter):
    id_ = input("Soğutulacak nesnenin ID'si: ").strip()
    nesne = nesne_bul(envanter, id_)
    if nesne is None:
        print("Nesne bulunamadı.")
        return

    if hasattr(nesne, "acil_durum_sogutmasi"):
        nesne.acil_durum_sogutmasi()
        print("Yeni durum:", nesne.durum_bilgisi())
    else:
        print("Bu nesne soğutulamaz!")


def main():
    envanter = []
    try:
        while True:
            print("\nKUANTUM AMBARI KONTROL PANELİ")
            print("1. Yeni Nesne Ekle (Rastgele)")
            print("2. Tüm Envanteri Listele")
            print("3. Nesneyi Analiz Et (ID ile)")
            print("4. Acil Durum Soğutması Yap")
            print("5. Çıkış")
            secim = input("Seçiminiz: ").strip()

            if secim == "1":
                yeni_nesne_ekle(envanter)
            elif secim == "2":
                envanteri_listele(envanter)
            elif secim == "3":
                nesne_analiz_et(envanter)
            elif secim == "4":
                sogutma_yap(envanter)
            elif secim == "5":
                break
            else:
                print("Geçersiz seçim!")
    except KuantumCokusuException as ex:
        print()
        print("SİSTEM ÇÖKTÜ! TAHLİYE BAŞLATILIYOR...")
        print(ex)
        sys.exit(1)


if __name__ == "__main__":
    main()


