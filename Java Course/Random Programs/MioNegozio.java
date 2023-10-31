import java.util.*;


/*----------------------------------------
 * 
 *             | Prodotto |
 * 
---------------------------------------- */
class Prodotto {
    String nome;
    double prezzo;
    String descrizione; 
    int quantita;
    String codice;

    Prodotto(String nome, double prezzo, String descrizione, int quantita, String codice) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        if (quantita > 0) {
            this.quantita = quantita;
        } else {
            this.quantita = 0;
            System.out.println("Quantita non valida");
        }

        this.codice = "B" + codice;
    }
}


/*----------------------------------------
 * 
 *             | Negozio |
 * 
---------------------------------------- */
class Negozio {
    Map<String, Prodotto> prodotti = new HashMap<>();

    void creaProdotto(String nome, double prezzo, String descrizione, int quantita, String codice) {
        prodotti.put(nome, new Prodotto(nome, prezzo, descrizione, quantita, codice));
    }

    boolean cercaProdotto(String nome, String codice) {
        if (prodotti.containsKey(nome) && prodotti.get(nome).codice.equals(codice)) {
            return true;
        } else {
            return false;
        }
    }

    void modificaProdotto(Utente admin, String nomeProdotto, String codiceProdotto) {
        if (cercaProdotto(nomeProdotto, codiceProdotto)) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Cosa vuoi modificare?");
            System.out.println("1. Nome");
            System.out.println("2. Prezzo");
            System.out.println("3. Descrizione");
            System.out.println("4. Quantita");
            System.out.println("5. Codice");
            System.out.println("6. Esci");

            int scelta;

            scanner.close();
            do {
                scelta = scanner.nextInt();
                switch (scelta) { // 1. Nome 2. Prezzo 3. Descrizione 4. Quantita 5. Codice 6. Esci
                    case 1: //Nome
                        System.out.println("Inserisci il nuovo nome:");
                        String nuovoNome = scanner.next();
                        prodotti.get(nomeProdotto).nome = nuovoNome;
                        System.out.println("Modifica completata.");
                        break;
                    case 2: //Prezzo
                        System.out.println("Inserisci il nuovo prezzo:");
                        double nuovoPrezzo = scanner.nextDouble();
                        prodotti.get(nomeProdotto).prezzo = nuovoPrezzo;
                        System.out.println("Modifica completata.");
                        break;
                    case 3: //Descrizione
                        System.out.println("Inserisci la nuova descrizione:");
                        String nuovaDescrizione = scanner.next();
                        prodotti.get(nomeProdotto).descrizione = nuovaDescrizione;
                        System.out.println("Modifica completata.");
                        break;
                    case 4: //Quantita
                        System.out.println("Inserisci la nuova quantita:");
                        int nuovaQuantita = scanner.nextInt();
                        prodotti.get(nomeProdotto).quantita = nuovaQuantita;
                        System.out.println("Modifica completata.");
                        break;
                    case 5: //Codice
                        System.out.println("Inserisci il nuovo codice:");
                        String nuovoCodice = scanner.next();
                        prodotti.get(nomeProdotto).codice = nuovoCodice;
                        System.out.println("Modifica completata.");
                        break;
                    case 6: //Esci
                        System.out.println("Uscita dal menu di modifica.");
                        break;
                    default: //Scelta non valida
                        System.out.println("Scelta non valida, riprova");
                }
            } while (scelta != 6); //Ripeto processo modifica fin quando scelta != 6
        }
    }

    void visualizzaProdotti() {
        for (Prodotto prodotto : prodotti.values()) {
            System.out.println(prodotto.nome + ": " + prodotto.prezzo);
        }
    }

    void eliminaProdotto(String nome) {
        prodotti.remove(nome);
    }
}


    /*----------------------------------------
    * 
    *               | USER |
    * 
    ---------------------------------------- */

class Utente {
        String tipo;

        Utente(String tipo) {
            this.tipo = tipo;
        }


        void visualizzaProdotti(Negozio negozio) {
            negozio.visualizzaProdotti();
        }

        //CONTROLLO STATO UTENTE (ADMIN / USER)
        boolean ControlloUtente (Utente utente) {
            if (tipo.equals("ADMIN")) {
                return true;
            }
            return false;
        }

        // MENU USER        
        void visualizzaMenuUser (Utente utente, Negozio negozio) {
            System.out.println(" ----------- ");
            System.out.println("| USER MENU |");
            System.out.println(" ----------- ");
            System.out.println("1. Visualizza prodotti");
            System.out.println("2. Esci");

            Scanner scanner = new Scanner(System.in);

            int scelta = scanner.nextInt();
            do {
                if (scelta < 1 || scelta > 5)  System.out.println("Scelta non valida, riprova.");
                scelta = scanner.nextInt();
            } while (scelta < 1 || scelta > 5);
            
            switch (scelta) {
                case 1: //Visualizza prodotti
                    visualizzaProdotti(negozio);
                    break;
            
                case 2: //Esci
                    System.out.println("Arrivederci");
                    break;
                
                default:
                    break;

            }
            scanner.close();
        }

    }


    /*----------------------------------------
    * 
    *               | ADMIN |
    * 
    ---------------------------------------- */

class Admin extends Utente {
        Admin (String tipo) {
            super(tipo);
        }

        void creaProdotto(Utente utente, Negozio negozio, String nome, double prezzo, String descrizione, int quantita, String codice) {
            if (ControlloUtente (utente)) {
                negozio.creaProdotto(nome, prezzo, descrizione, quantita, codice);
            }

            else {
                System.out.println("Utente non autorizzato");
            }
        }

        void eliminaProdotto(Utente utente, Negozio negozio, String nome) {
            if (ControlloUtente (utente)) {
                negozio.eliminaProdotto(nome);
            }
            else {
                System.out.println("Utente non autorizzato");
            }
        }
        boolean modificaProdotto(Utente utente, Negozio negozio, String nomeProdotto, String codiceProdotto) {
        if (ControlloUtente(utente)) {
            negozio.modificaProdotto(utente, nomeProdotto, codiceProdotto);
            return true;
        } 
        return false;
        }

        void visualizzaMenuAdmin (Utente utente, Negozio negozio) {
        System.out.println(" ------------ ");
        System.out.println("| ADMIN MENU |");
        System.out.println(" ------------ ");
        System.out.println("1. Visualizza prodotti");
        System.out.println("2. Crea prodotto");
        System.out.println("3. Modifica prodotto");
        System.out.println("4. Elimina prodotto");
        System.out.println("5. Esci");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci la tua scelta: ");
        int scelta;
        do {
            scelta = scanner.nextInt();
            if (scelta < 1 || scelta > 5) {
                System.out.println("Scelta non valida, riprova.");
            }
        } while (scelta < 1 || scelta > 5);

        
        switch (scelta) {
            case 1: //Visualizza prodotti
                visualizzaProdotti(negozio);
                break;
        
            case 2: //Crea prodotto
                System.out.println("Inserisci il nome del prodotto:");
                String nome = scanner.next();
                System.out.println("Inserisci il prezzo del prodotto:");
                double prezzo = scanner.nextDouble();
                System.out.println("Inserisci la descrizione del prodotto:");
                String descrizione = scanner.next();
                System.out.println("Inserisci la quantita del prodotto:");
                int quantita = scanner.nextInt();
                System.out.println("Inserisci il codice del prodotto:");
                String codice = scanner.next();
                creaProdotto(utente, negozio, nome, prezzo, descrizione, quantita, codice);
                break;

            case 3: //Modifica prodotto
                System.out.println("Inserisci il nome del prodotto da modificare:");
                String nomeMod = scanner.next();
                System.out.println("Inserisci il codice del prodotto da modificare:");
                codice = scanner.next();
                if (modificaProdotto(utente, negozio, nomeMod, codice)) System.out.println("Tentativo di modifica riuscito!");
                else System.out.println("Tentativo di modifica fallito!");
                break;

            case 4: //Elimina prodotto
                System.out.println("Inserisci il nome del prodotto da eliminare:");
                String nomeElim = scanner.next();
                eliminaProdotto(utente, negozio, nomeElim);
                break;

            case 5: //Esci
                System.out.println("Arrivederci");
                break;
            
            default:
                break;
        }
        scanner.close();
    }
}




//CLASSE MAIN
public class MioNegozio {
    public static void main(String[] args) {
        Negozio negozio = new Negozio();
        Admin admin = new Admin("ADMIN");
        Utente user = new Utente("USER");

        admin.creaProdotto(admin, negozio, "Prodotto1", 10.0, "Descrizione1", 5, "123");
        user.visualizzaProdotti(negozio);

        admin.modificaProdotto(admin, negozio, "Prodotto1", "123");
        user.visualizzaProdotti(negozio);

        admin.eliminaProdotto(admin, negozio, "Prodotto1");
        user.visualizzaProdotti(negozio);
        
        // Esempio di utilizzo dell'interfaccia utente per un admin
        admin.visualizzaMenuUser(admin, negozio);
        
        // Esempio di utilizzo dell'interfaccia utente per un utente generico
        user.visualizzaMenuUser(user, negozio);
    }
}