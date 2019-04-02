Antonio Dan Macovei, 324CA

Data începerii temei: 27.10.2018
Data finalizării temei: 04.11.2018

Implementarea jocului Seriful din Nottingham este structurata in 
felul urmator:

- Logica jocului este inglobata in pachetul Main.
Implementarea rularii jocului si a rundelor se afla in clasa Game, unde
se initializeaza datele jocului curent in constructor, dupa care se joaca
rundele in functie de rolul ales pentru fiecare jucator (serif sau 
comerciant).

La fiecare tura, jucatorii sunt reinstantiati sub forma tipului de jucator
impus, pastrand datele anterioare. La prima trecere prin lista de jucatori,
comerciantii isi joaca tura, urmand ca seriful sa vina la final. Dupa ce toti
jucatorii si-au terminat tura, se adauga cartile bonus (aduse de cele ilegale)
la cele din sac, iar mai apoi toate cartile din sac sunt transferate pe taraba
si goliti sacii.
La terminarea tuturor rundelor, se calculeaza scorul final in functie de
bunurile de pe taraba si bonusurile acordate pentru King si Queen. Apoi
jucatorii sunt sortati in functie de punctajul final si afisati.

In acelasi pachet se gasesc si clase ajutatoare, GameInput care citeste datele
jocului, Deck care construieste pachetul de carti al jocului si Main, de unde
se creaza o noua instanta de joc (Game).

- Bunurile sunt introduse in joc sub forma mai multor clase din pachetul 
Goods. Superclasa Good este extinsa de fiecare subtip (Apple, Bread, 
Cheese, Chicken, Silk, Pepper, Barrel), fiecare avand getters si setters 
pentru atributele lor.

De asemenea, superclasa Good ofera implementarea unei metode care calculeaza
cine trebuie sa primeasca bonusul King sau Queen pentru fiecare bun legal,
determinand numarul de aparitii al bunurilor pe tarabele jucatorilor.

- Pachetul Players contine 3 clase:
-- O superclasa Player care defineste metode abstracte folosite in subclase
si implementeaza metode care ajuta la gestionarea joculului (exemplu:
refacerea numarului de carti in mana, reinitializarea datelor unui jucator
si calcularea punctajului in functie de bunurile de pe taraba.
-- Subclasele Sheriff si Merchant care implementeaza metoda playTurn().
In aceasta metoda se executa logica fiecarei ture in functie de tipul
jucatorului. Pentru serif se verifica sacii jucatorilor si se accepta
sau nu mita, iar pentru comercianti se adauga cartile din mana in sac
si se declara bunurile.

- Pachetul Strategies contine o superclasa Strategy care este extinsa
de cele 3 strategii, Base, Greedy si Bribe. In functie de atributul
setat in Player care numeste strategia curenta, aici se executa fiecare
actiune descrisa in subclasele Sheriff si Merchant. De exemplu, in Base
metoda fillBag() va alege bunul cel mai frecvent si il va adauga in sac.
Aceste subclase implementeaza atat metodele pentru serifi cat si pentru
comercianti.


Observatie:

Campul ID este public si static pentru a putea fi folosit in parti ale
programului fara a avea o instanta a bunului respectiv. Nu am folosit getter
pentru el deoarece metoda nu ar fi putut fi declarata ca abstract in 
superclasa daca era statica.
