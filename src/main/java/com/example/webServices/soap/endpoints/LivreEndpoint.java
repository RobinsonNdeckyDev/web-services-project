package com.example.webServices.soap.endpoints;

import com.example.webServices.dtos.LivreDTO;
import com.example.webServices.services.LivreService;
import com.example.webServices.services.exceptions.LivreNonTrouveException;
import com.example.webServices.soap.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@Endpoint
public class LivreEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/webServices";
    private final LivreService livreService;
    private static final Logger logger = LoggerFactory.getLogger(LivreEndpoint.class);

    public LivreEndpoint(LivreService livreService) {
        this.livreService = livreService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AjouterLivreRequest")
    @ResponsePayload
    public AjouterLivreResponse ajouterLivre(@RequestPayload AjouterLivreRequest request) {
        if (request.getTitre() == null || request.getTitre().isBlank()
                || request.getAuteur() == null || request.getAuteur().isBlank()
                || request.getIsbn() == null || request.getIsbn().isBlank()) {
            throw new ValidationException("Les champs titre, auteur et isbn sont obligatoires.");
        }

        livreService.ajouterLivre(
                new LivreDTO(null,
                        request.getTitre(),
                        request.getAuteur(),
                        request.getIsbn(),
                        request.isEstDisponible())
        );

        logger.info("Livre ajouté : {} - {}", request.getTitre(), request.getAuteur());

        AjouterLivreResponse resp = new AjouterLivreResponse();
        resp.setMessage("Livre ajouté avec succès !");
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ModifierLivreRequest")
    @ResponsePayload
    public ModifierLivreResponse modifierLivre(@RequestPayload ModifierLivreRequest request) {
        long id = request.getLivreId();
        if (id <= 0) {
            throw new ValidationException("L'ID du livre est obligatoire pour la modification.");
        }

        try {
            livreService.modifierLivre(id,
                    new LivreDTO(id,
                            request.getNouveauLivre().getTitre(),
                            request.getNouveauLivre().getAuteur(),
                            request.getNouveauLivre().getIsbn(),
                            request.getNouveauLivre().isEstDisponible())
            );
            logger.info("Livre modifié avec ID : {}", id);
        } catch (LivreNonTrouveException ex) {
            throw new ValidationException("Livre non trouvé pour modification.");
        }

        ModifierLivreResponse resp = new ModifierLivreResponse();
        resp.setSuccess(true);
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SupprimerLivreRequest")
    @ResponsePayload
    public SupprimerLivreResponse supprimerLivre(@RequestPayload SupprimerLivreRequest request) {
        long id = request.getLivreId();
        if (id <= 0) {
            throw new ValidationException("L'ID du livre est obligatoire pour la suppression.");
        }

        try {
            livreService.supprimerLivre(id);
            logger.info("Livre supprimé avec ID : {}", id);
        } catch (LivreNonTrouveException ex) {
            throw new ValidationException("Livre non trouvé pour suppression.");
        }

        SupprimerLivreResponse resp = new SupprimerLivreResponse();
        resp.setSuccess(true);
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PreterLivreRequest")
    @ResponsePayload
    public PreterLivreResponse preterLivre(@RequestPayload PreterLivreRequest request) {
        long uid = request.getUserId();
        long lid = request.getLivreId();
        if (uid <= 0 || lid <= 0) {
            throw new ValidationException("Les IDs utilisateur et livre sont obligatoires pour un prêt.");
        }

        try {
            boolean success = livreService.preterLivre(uid, lid);
            if (!success) {
                throw new ValidationException("Le livre n'est pas disponible pour le prêt.");
            }
            logger.info("Livre prêté : LivreID={} à UserID={}", lid, uid);

            PreterLivreResponse resp = new PreterLivreResponse();
            resp.setSuccess(true);
            return resp;
        } catch (LivreNonTrouveException ex) {
            throw new ValidationException("Livre non trouvé pour prêt.");
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RetournerLivreRequest")
    @ResponsePayload
    public RetournerLivreResponse retournerLivre(@RequestPayload RetournerLivreRequest request) {
        long uid = request.getUserId();
        long lid = request.getLivreId();
        if (uid <= 0 || lid <= 0) {
            throw new ValidationException("Les IDs utilisateur et livre sont obligatoires pour un retour.");
        }

        try {
            boolean success = livreService.retournerLivre(uid, lid);
            if (!success) {
                throw new ValidationException("Le retour a échoué. Le livre n'était pas prêté à cet utilisateur.");
            }
            logger.info("Livre retourné : LivreID={} par UserID={}", lid, uid);

            RetournerLivreResponse resp = new RetournerLivreResponse();
            resp.setSuccess(true);
            return resp;
        } catch (LivreNonTrouveException ex) {
            throw new ValidationException("Livre non trouvé pour retour.");
        }
    }

    @SoapFault(faultCode = FaultCode.CLIENT)
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
