## Kuantum Kaos Yönetimi - Çok Dilli Ödev

Bu ödevi hazırlarken önce verilen senaryodaki OOP gereksinimlerini (abstract sınıf, kapsülleme, interface/IKritik, kalıtım ve polimorfizm, özel exception, menü tabanlı ana oyun döngüsü) net olarak parçalara ayırdım ve her dil için aynı mimariyi tekrar eden bir yapı kurdum. `KuantumNesnesi` soyut sınıfında `ID`, kapsüllenmiş `Stabilite` ve `TehlikeSeviyesi` özelliklerini tanımlayıp, stabilite değiştikten sonra 0 veya altına inmesi durumunda `KuantumCokusuException` fırlatacak şekilde tasarladım; böylece çöküş kontrolü tek noktadan yönetildi. `VeriPaketi`, `KaranlikMadde` ve `AntiMadde` sınıfları bu temeli miras alıp analiz davranışlarını ve sadece kritik nesnelerde bulunan `AcilDurumSogutmasi` metodunu farklılaştırdı. Main kısımda C# için Visual Studio'ya uygun bir Console uygulamasında, Java için `Main.java`, Python için `kuantum_kaos.py`, JavaScript için `kuantum_kaos.js` dosyalarında menü tabanlı sonsuz döngü kurarak `List<KuantumNesnesi>` (veya dildeki eşdeğer koleksiyon) ile envanter tuttum, ID ile nesne bulup `is/instanceof/isinstance` mantığıyla yalnızca IKritik olan nesnelerde soğutma işlemini aktif hale getirdim ve herhangi bir stabilite çöküşünde `try-catch` içinde “SİSTEM ÇÖKTÜ! TAHLİYE BAŞLATILIYOR...” mesajını bastırarak programı güvenli şekilde sonlandırdım.

### Klasörler
- `CSharpQuantum` : Visual Studio Console App için `Program.cs`
- `JavaQuantum`   : `Main.java` (tek dosya, `javac Main.java` ile derlenebilir)
- `PythonQuantum` : `kuantum_kaos.py` (`python kuantum_kaos.py` ile çalışır)
- `JSQuantum`     : `kuantum_kaos.js` (`node kuantum_kaos.js` ile çalışır)




