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

**Ghid de utilizare**


S-a accesat calea fișierului cu aplicație și s-a construit imaginea:

<img width="1043" height="325" alt="image" src="https://github.com/user-attachments/assets/a372c797-fd67-4585-a8e1-8d8cb24b7a07" />

S-a rulat containerul:

<img width="1060" height="63" alt="image" src="https://github.com/user-attachments/assets/49d7d244-f970-46bb-8062-fee887ffacd7" />

S-a efectuat comenzile init, și status:

<img width="1050" height="241" alt="image" src="https://github.com/user-attachments/assets/f02ecfa0-7148-4c1c-b610-b35a056914bb" />

S-a efectuat primul commit, și instrucțiunea log care prezintă istoricul commit-urilor cu data mesajul și numărul de fișiere:

<img width="634" height="473" alt="image" src="https://github.com/user-attachments/assets/05b6be17-9946-470a-a947-07155a4369dc" />

S-a efectuat comanda list care afișează lista de commit-uri:

<img width="720" height="223" alt="image" src="https://github.com/user-attachments/assets/c39bbff4-71c8-4be3-9eeb-c253278c072f" />

S-a efectuat comanda restore, care restaurează commit-ul indicat:

<img width="1009" height="114" alt="image" src="https://github.com/user-attachments/assets/acfa8f71-2c47-4886-b5eb-85260dabaeae" />





