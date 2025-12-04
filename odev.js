const readline = require("readline");

class KuantumCokusuException extends Error {
  constructor(nesneId) {
    super(`Kuantum Çöküşü! Patlayan nesne ID: ${nesneId}`);
    this.name = "KuantumCokusuException";
    this.nesneId = nesneId;
  }
}

class KuantumNesnesi {
  constructor(id, stabilite, tehlikeSeviyesi) {
    this._id = id;
    this._stabilite = 0;
    this.id = id;
    this.tehlikeSeviyesi = Math.min(10, Math.max(1, tehlikeSeviyesi));
    this.stabilite = stabilite;
  }

  get id() {
    return this._id;
  }

  set id(v) {
    this._id = v;
  }

  get stabilite() {
    return this._stabilite;
  }

  set stabilite(value) {
    if (value > 100) value = 100;
    if (value < 0) value = 0;
    this._stabilite = value;
  }

  stabiliteDegistir(delta) {
    this.stabilite = this.stabilite + delta;
    if (this.stabilite <= 0) {
      throw new KuantumCokusuException(this.id);
    }
  }

  analizEt() {
    throw new Error("analizEt() uygulanmalı");
  }

  durumBilgisi() {
    return `ID: ${this.id}, Stabilite: ${this.stabilite.toFixed(
      2
    )}, Tehlike: ${this.tehlikeSeviyesi}`;
  }
}

class VeriPaketi extends KuantumNesnesi {
  analizEt() {
    console.log("Veri içeriği okundu.");
    this.stabiliteDegistir(-5);
  }
}

class KaranlikMadde extends KuantumNesnesi {
  analizEt() {
    console.log("Karanlık madde titreşimi analiz ediliyor...");
    this.stabiliteDegistir(-15);
  }

  acilDurumSogutmasi() {
    console.log("Karanlık madde için acil soğutma uygulanıyor...");
    this.stabiliteDegistir(+50);
  }
}

class AntiMadde extends KuantumNesnesi {
  analizEt() {
    console.log("Evrenin dokusu titriyor...");
    this.stabiliteDegistir(-25);
  }

  acilDurumSogutmasi() {
    console.log("Anti madde için ACİL soğutma uygulanıyor!");
    this.stabiliteDegistir(+50);
  }
}

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

function soru(s) {
  return new Promise((resolve) => rl.question(s, resolve));
}

const envanter = [];

function rastgeleAralik(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function yeniNesneEkle() {
  const tip = rastgeleAralik(0, 2);
  const id = "N" + (envanter.length + 1);
  const stabilite = rastgeleAralik(50, 100);
  const tehlike = rastgeleAralik(1, 10);

  let nesne;
  if (tip === 0) {
    nesne = new VeriPaketi(id, stabilite, tehlike);
    console.log(`VeriPaketi oluşturuldu. ID: ${id}`);
  } else if (tip === 1) {
    nesne = new KaranlikMadde(id, stabilite, tehlike);
    console.log(`KaranlikMadde oluşturuldu. ID: ${id}`);
  } else {
    nesne = new AntiMadde(id, stabilite, tehlike);
    console.log(`AntiMadde oluşturuldu. ID: ${id}`);
  }

  envanter.push(nesne);
}

function envanteriListele() {
  if (envanter.length === 0) {
    console.log("Envanter boş.");
    return;
  }
  for (const n of envanter) {
    console.log(n.durumBilgisi());
  }
}

function nesneBul(id) {
  return envanter.find((n) => n.id === id) || null;
}

async function nesneAnalizEt() {
  const id = (await soru("Analiz edilecek nesnenin ID'si: ")).trim();
  const nesne = nesneBul(id);
  if (!nesne) {
    console.log("Nesne bulunamadı.");
    return;
  }
  nesne.analizEt();
  console.log("Yeni durum:", nesne.durumBilgisi());
}

async function sogutmaYap() {
  const id = (await soru("Soğutulacak nesnenin ID'si: ")).trim();
  const nesne = nesneBul(id);
  if (!nesne) {
    console.log("Nesne bulunamadı.");
    return;
  }

  if (typeof nesne.acilDurumSogutmasi === "function") {
    nesne.acilDurumSogutmasi();
    console.log("Yeni durum:", nesne.durumBilgisi());
  } else {
    console.log("Bu nesne soğutulamaz!");
  }
}

async function main() {
  try {
    while (true) {
      console.log("\nKUANTUM AMBARI KONTROL PANELİ");
      console.log("1. Yeni Nesne Ekle (Rastgele)");
      console.log("2. Tüm Envanteri Listele");
      console.log("3. Nesneyi Analiz Et (ID ile)");
      console.log("4. Acil Durum Soğutması Yap");
      console.log("5. Çıkış");
      const secim = (await soru("Seçiminiz: ")).trim();

      if (secim === "1") {
        yeniNesneEkle();
      } else if (secim === "2") {
        envanteriListele();
      } else if (secim === "3") {
        await nesneAnalizEt();
      } else if (secim === "4") {
        await sogutmaYap();
      } else if (secim === "5") {
        break;
      } else {
        console.log("Geçersiz seçim!");
      }
    }
    rl.close();
  } catch (err) {
    if (err instanceof KuantumCokusuException) {
      console.log();
      console.log("SİSTEM ÇÖKTÜ! TAHLİYE BAŞLATILIYOR...");
      console.log(err.message);
    } else {
      console.error("Beklenmeyen hata:", err);
    }
    rl.close();
    process.exit(1);
  }
}

main();

