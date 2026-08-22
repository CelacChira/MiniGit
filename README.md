**MiniGit**

Un mic program în Java care face pe scurt ce face și Git, dar mult mai simplu. E un sistem simplu de control al 
versiunilor care funcționează ca un backup pentru fișiere. Aplicația a fost scrisă în limbajul Java și 
containerizată folosind Docker pentru a demonstra izolarea și portabilitatea codului. 
Aplicația MiniGit permite utilizatorului să salveze starea curentă a tuturor fișierelor dintr-un 
director printr-o comandă numită commit. Când se execută un commit, programul parcurge toate 
fișierele din folder, le copiază într-un director special numit .minigit, și le păstrează acolo ca un 
snapshot complet. Fiecare snapshot primește un identificator unic bazat pe timpul curent, astfel încât 
utilizatorul poate reveni exact la acel moment din trecut când a salvat commit-ul. 

**Ce face**

Programul ține minte schimbările pe care le faci la un proiect, la fel ca Git, dar cu comenzi puține și ușor de înțeles.

Poți să:
- pornești un „repository" nou (`init`)
- salvezi o versiune nouă, cu un mesaj (`commit`)
- vezi lista cu toate versiunile salvate (`log`)
- vezi ce s-a schimbat (`status`)
- te întorci la o versiune veche (`restore`)
- vezi diferențele dintre versiuni (`diff`)
- vezi toate versiunile salvate (`list`)
- vezi lista de comenzi (`help`)
- ieși din program (`exit`)

**De ce am făcut acest proiect**

Acest proiect este un exercițiu simplu, făcut ca să înțeleg mai bine cum funcționează Git pe dinăuntru.
