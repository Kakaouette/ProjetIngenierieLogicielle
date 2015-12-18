CREATE TRIGGER historique_commission AFTER UPDATE ON dossier
 FOR EACH ROW BEGIN
	DECLARE compte_id integer;
    DECLARE historique_id integer;
	SET compte_id = (SELECT ID FROM compte WHERE LOGIN="Serveur");
    IF OLD.ETAT = 'traité_secretariat_formation' 
    	AND NEW.ETAT = 'en_attente_commission' 
        THEN
        IF ISNULL(compte_id) THEN
			INSERT INTO compte (LOGIN, MDP, NOM, PRENOM, TYPE)
			VALUES('Serveur', 'Serveur', 'Serveur', 'Serveur', 'admin');
			SET compte_id = (SELECT ID FROM compte WHERE login='Serveur');
		END IF;

		SET historique_id = (SELECT MAX(ID) FROM historique) + 1;
        
        INSERT INTO historique (ID, ACTION,DATE, MESSAGE,COMPTE_ID)
		VALUES (historique_id, 'Changement automatique', SYSDATE(), 'Changement automatique pour la commission.', compte_id);
        
        		
		INSERT INTO dossier_historique (DOSSIER_ID, HISTORIQUE_ID)
		VALUES (old.ID, historique_id);	


	END IF;
END;