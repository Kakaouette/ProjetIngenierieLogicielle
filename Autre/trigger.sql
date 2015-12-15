DROP TRIGGER IF EXISTS Historique_Commission;
CREATE TRIGGER Historique_Commission
AFTER UPDATE ON dossier
FOR EACH ROW
SET @historique_id = (SELECT MAX(ID) FROM historique),
    @compte_id = (SELECT ID FROM compte WHERE LOGIN='Serveur');
		

BEGIN
	IF (OLD.ETAT = 'traité_secretariat_formation') AND (NEW.ETAT = 'en_attente_commission') 
		THEN
		
		-- Si inexistant on le cree
		IF ISNULL(compte_id) THEN
			INSERT INTO compte (LOGIN, MDP, NOM, PRENOM, TYPE)
			VALUES('Serveur', 'Serveur', 'Serveur', 'Serveur', 'admin');
			
			@compte_id = SELECT ID FROM compte WHERE login='Serveur';
		END IF;
		
		DECLARE historique_id INT;
	-- On récupère l'id max pour une nouvelle ligne dans l'historique
	SET @historique_id = (SELECT MAX(ID) FROM historique)
		
		-- Création de la ligne dans l'historique
		INSERT INTO historique (ID, ACTION, COMPTE_ID, DATE, MESSAGE)
		VALUES (historique_id, 'Changement automatique', compte_id, SYSDATE, 'Changement automatique pour la commission.');
		
		-- Ajout de la ligne dans la table de jointure
		INSERT INTO dossier_historique (DOSSIER_ID, HISTORIQUE_ID)
		VALUES (old.ID, historique_id);	
	END IF;
END;