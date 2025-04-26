package com.example.webServices.soap.endpoints;

import com.example.webServices.dtos.LivreDTO;
import com.example.webServices.services.LivreService;
import com.example.webServices.soap.models.AjouterLivreRequest;
import com.example.webServices.soap.models.AjouterLivreResponse;
import com.example.webServices.soap.models.ModifierLivreRequest;
import com.example.webServices.soap.models.ModifierLivreResponse;
import com.example.webServices.soap.models.SupprimerLivreRequest;
import com.example.webServices.soap.models.SupprimerLivreResponse;
import com.example.webServices.soap.models.PreterLivreRequest;
import com.example.webServices.soap.models.PreterLivreResponse;
import com.example.webServices.soap.models.RetournerLivreRequest;
import com.example.webServices.soap.models.RetournerLivreResponse;
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

    public LivreEndpoint(LivreService livreService) {
        this.livreService = livreService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AjouterLivreRequest")
    @ResponsePayload
    public AjouterLivreResponse ajouterLivre(@RequestPayload AjouterLivreRequest request) {
        // Validation
        if (request.getTitre() == null || request.getTitre().isBlank()
                || request.getAuteur() == null || request.getAuteur().isBlank()
                || request.getIsbn() == null  || request.getIsbn().isBlank()) {
            throw new ValidationException("Les champs titre, auteur et isbn sont obligatoires.");
        }
        // Création du DTO et appel service
        livreService.ajouterLivre(
                new LivreDTO(null,
                        request.getTitre(),
                        request.getAuteur(),
                        request.getIsbn(),
                        request.isEstDisponible())
        );
        // Réponse
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
        // Appel du service (qui retourne le DTO mis à jour)
        livreService.modifierLivre(id,
                new LivreDTO(id,
                        request.getNouveauLivre().getTitre(),
                        request.getNouveauLivre().getAuteur(),
                        request.getNouveauLivre().getIsbn(),
                        request.getNouveauLivre().isEstDisponible())
        );
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
        livreService.supprimerLivre(id);
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
        boolean success = livreService.preterLivre(uid, lid);
        PreterLivreResponse resp = new PreterLivreResponse();
        resp.setSuccess(success);
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RetournerLivreRequest")
    @ResponsePayload
    public RetournerLivreResponse retournerLivre(@RequestPayload RetournerLivreRequest request) {
        long uid = request.getUserId();
        long lid = request.getLivreId();
        if (uid <= 0 || lid <= 0) {
            throw new ValidationException("Les IDs utilisateur et livre sont obligatoires pour un retour.");
        }
        boolean success = livreService.retournerLivre(uid, lid);
        RetournerLivreResponse resp = new RetournerLivreResponse();
        resp.setSuccess(success);
        return resp;
    }

    @SoapFault(faultCode = FaultCode.CLIENT)
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}