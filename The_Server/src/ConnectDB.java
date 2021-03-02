
import com.sun.deploy.security.SelectableSecurityManager;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    ResultSet resultats = null , result = null;
    Statement stmt  ,stmt2 ;
    Connection conn;
    Serv sv;
    String requete = "" , seance="";
    int nbMAJ;

    public ConnectDB(Serv sv) {
        this.sv = sv;
        connectToDB();

    }

    public void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:mysql://localhost:3306/cabinet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
            String user = "hana";
            String passwd = "Basic@99";


            conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion au BD effective !");


        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    public void select() throws IOException {
        int id=getID();
        requete="";
        requete = "SELECT patient.* , facture.total , facture.paye , facture.debut , facture.fin FROM patient LEFT JOIN facture  ";
        requete += "ON patient.id = facture.id_patient ";
        requete += " WHERE id =  " +id +" ; ";
        seance ="SELECT seance.temps FROM seance WHERE patient_consulte = " +id +" ; ";
        System.out.println(requete);
        System.out.println(seance);
        try {
            stmt = conn.createStatement();
            resultats = stmt.executeQuery(requete);
            stmt2 = conn.createStatement();
            result = stmt2.executeQuery(seance);
        } catch (SQLException e) {
            System.out.println("Anomalie lors de l'execution de la requête");
        }

        String ch;
        try {
            ResultSetMetaData rsmd = resultats.getMetaData();
            int nbCols = rsmd.getColumnCount();

            boolean encore = resultats.next();
            boolean encore1 = result.next();

            if (!encore)
                sv.Send("patient not found.");
            else {
                while (encore) {//sending lines
                    for (int i = 1; i <= nbCols; i++) {//sending columns
                        ch = resultats.getString(i);
                        if (resultats.wasNull())
                            ch = "unknown";

                        sv.Send(ch);
                    }

                    encore = resultats.next();
                }
            }


            if (!encore1)
                sv.Send("no appointments");
            else{
                ch="";
                while (encore1)
                {
                    ch +=result.getString(1) +" /  ";
                    encore1=result.next();
                }
                sv.Send(ch);
            }
            resultats.close();
            result.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void insert( ) throws IOException{
        requete="";
        String nom=sv.receive() , prenom=sv.receive() , numero = sv.receive() , createF;

        requete =" INSERT INTO patient(nom , prenom , numeroTel , email) " ;
        requete += "values ( \""  + nom +"\"  ,  \"" + prenom +"\" , \"" + numero +"\"" ;

        String email=sv.receive();
        if (email.equals(""))
            requete += ", NULL );"  ;
        else
            requete += ", \"" + email +"\" );"  ;

        System.out.println(requete);
        try {
            stmt = conn.createStatement();
            nbMAJ =stmt.executeUpdate(requete);

            int id=0;

            String getId = "SELECT id from patient ";
            getId +=  " WHERE nom = \""+ nom +"\" AND prenom = \"" + prenom +"\" AND numeroTel = \""+numero+ "\";";
            stmt2 = conn.createStatement();
            resultats = stmt2.executeQuery(getId);
            if (resultats.next() )
                {
                    String  idCh= resultats.getString(1);
                    id =Integer.parseInt(idCh);
                    createF="INSERT INTO facture (id_patient) ";
                    createF += "VALUES ( " + id + " ) ;";
                    nbMAJ=stmt.executeUpdate(createF);

                }
                    System.out.println(id);
                    resultats.close();


            sv.Send( "Patient added successfully.");


        } catch (SQLException e) {
            System.out.println("Anomalie lors de l'execution de la requête");
            System.out.println(e.getMessage());


        }

    }




    public int getID()
    {        int id=0;

        String getId = "SELECT id from patient " , idCh;


        try {
            getId +=  " WHERE nom = \""+ sv.receive()+"\" AND prenom = \"" + sv.receive() +"\" AND numeroTel = \""+sv.receive()+ "\";";
            stmt = conn.createStatement();
            resultats = stmt.executeQuery(getId);
            if (resultats.next() )
            {idCh= resultats.getString(1);
            id =Integer.parseInt(idCh);}
            System.out.println(id);
            resultats.close();
        }
        catch (SQLException  | IOException e) {
            System.out.println("Anomalie lors de l'execution de la requête");
            System.out.println(e.getMessage());
            return id;

        }
        return id ;
    }
    public void delete() throws IOException{
        int nbMAJ ;
        String  facture="" ,seance=""  ; requete="";

       int id= getID();
         try{
            facture="DELETE FROM facture where id_patient =" + id +";";
            seance="DELETE FROM  seance where patient_consulte ="+id+";";
            requete =" DELETE FROM  patient WHERE id= "+id + ";";
            System.out.println(requete);


            nbMAJ = stmt.executeUpdate(facture);
            nbMAJ = stmt.executeUpdate(seance);
            nbMAJ =stmt.executeUpdate(requete);
            if (nbMAJ != 0)
                sv.Send( "Patient has been deleted.");
            else
                sv.Send( "Patient doesn't exist.");


         } catch (SQLException e) {
            System.out.println("Anomalie lors de l'execution de la requête");
            System.out.println(e.getMessage());

        }

    }

    public void schedule() throws  IOException{
        String jour= sv.receive();

        requete="";
        requete = "SELECT patient.nom , patient.prenom , patient.numeroTel , seance.temps  FROM patient LEFT JOIN seance  ";
        requete += "ON patient.id = seance.patient_consulte ";
        requete += "WHERE temps BETWEEN '" + jour + "' AND '" + jour + " 23:59:57' ;";

        System.out.println(requete);
        try {
            stmt = conn.createStatement();
            resultats = stmt.executeQuery(requete);
        } catch (SQLException e) {
            System.out.println("Anomalie lors de l'execution de la requête");
        }

        sendResponse(resultats);


    }

    public void update() throws  IOException{
                String requete="";
                int s= Integer.parseInt(sv.receive());
                int id=Integer.parseInt(sv.receive());
                int pay;
                switch (s){
                    case 1:
                        requete="UPDATE patient SET nom= \""+sv.receive()+"\" WHERE id = "+id +";";
                        break;
                    case 2:
                        requete="UPDATE patient SET prenom= \""+sv.receive()+"\" WHERE id = "+id +";";
                        break;
                    case 3:
                        requete="UPDATE patient SET numeroTel= \""+sv.receive()+"\" WHERE id = "+id +";";
                        break;
                    case 4:
                        requete="UPDATE patient SET email= \""+sv.receive()+"\" WHERE id = "+id +";";
                        break;
                    case 5:
                        pay=Integer.parseInt(sv.receive());
                        requete="UPDATE facture SET total= "+pay+" WHERE id_patient = "+id +";";
                        break;
                    case 6:
                        pay=Integer.parseInt(sv.receive());
                        requete="UPDATE facture SET paye= "+pay+" WHERE id_patient = "+id +";";
                        break;
                    case 7:
                        requete="UPDATE facture SET debut= '"+sv.receive()+"' WHERE id_patient = "+id +";";
                        break;
                    case 8:
                        requete="UPDATE facture SET fin= '"+sv.receive()+"' WHERE id_patient = "+id +";";
                        break;
                    case 9:
                        requete="INSERT INTO seance(temps , patient_consulte) values( '"+sv.receive()+"' , " +id+");";
                        break;

                }

                 System.out.println(requete);
                  try {
                     stmt = conn.createStatement();
                     nbMAJ =stmt.executeUpdate(requete);
                      sv.Send( "Patient modified successfully .");

                    } catch (SQLException e) {
                      System.out.println("Anomalie lors de l'execution de la requête");
                  }
    }

    //sending the results to the client
    public void sendResponse(ResultSet resultats) throws IOException {
        String row="" ;
        try {
            ResultSetMetaData rsmd = resultats.getMetaData();
            int nbCols = rsmd.getColumnCount();
            System.out.println("here...");
            boolean encore = resultats.next();

            if (!encore)
                sv.Send("no appointments on this day ");
            else {
                while (encore) {//sending lines
                    for (int i = 1; i <= nbCols; i++) {//sending columns
                        if (resultats.wasNull())
                            row = "unknown";

                        row += resultats.getString(i) +"\t\t";
                    }
                    sv.Send(row);
                    row="";

                    encore = resultats.next();
                }
            }
            sv.Send("fin");
            resultats.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void runOp(String op)  {

        try {

            switch (op) {
                 case "info":
                    select();
                    break;

                case  "add":
                    insert();
                    break;

                case  "delete":
                    delete();
                    break;

                case  "schedule":
                    schedule();
                    break;

                case  "modify":
                    update();
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


