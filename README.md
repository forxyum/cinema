Leírás:
-
Az alkalmazás egy mozi foglalás rendszerét valósítja meg, az asztali része egy admin felület, ahol filmeket, vetítéseket, termeket és foglalásokat adhatunk hozzá, míg a webes felületen a felhasználó regisztrálhat, illetve foglalásokat vehet fel a vetítésekre.

Funkciók:
-
Asztali:
- Filmek felvétele, módosítása, törlése, listázása
- Termek felvétele, módosítása, törlése, listázása
- Vetítések felvétele, módosítása, törlése, listázása
- Foglalások felvétele, módosítása, törlése, listázása

Asztali:
- Foglalások felvétele, listázása

Szükséges beállítások:
-
JDK 14 jelenléte a gépen

Path környezeti változóban a 14-es jdk bin mappája legyen megadva  
Ha az alapértelmezett helyre raktad fel, akkor ez a  
```C:\Program Files\Java\jdk-14\bin ```
lesz (de a verziószám bekavarhat az elérési útba, így inkább nézd meg).  
  
A projekt SDK-jának állítsd be a jdk mappáját (ugyan ez bin nélkül).  
IntelliJ-ben ezt a File -> Project Structure alatt lehet beállítani.  
  
Másold be a cinema-desk mappában található cinema.db fájlt a Tomcat bin mappádba (vagy írd át a ```cinema-desk/src/main/resources/db.properties``` fájl tartalmát abszolút elérési útra, így ugyan azt az adatbázist fogják használni).  
  
Configold be a Tomcat-et  
Ez IntelliJ-en Run -> Edit configuration -> bal fent + -> Tomcat server -> local -> tallózd be a Tomcat mappád -> ha fut valami a 8080-on írd át a portot -> jobb alul a piros fix-el húzd be a cinema-web:war artifact-ot -> done  
  
  mvn install-al készítsd el a jar-okat, majd javafx:compile javafx:run -al fut az asztali  
  a Tomcat futtatással elindul a webes, ennek a belépési pontja a ```http://localhost:"megadottport"/cinema_web_war/pages/register.jsp``` lesz
  
