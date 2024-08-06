package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }


    @GetMapping
    public List<CatCard> getCards() {
        return catCardDao.getCatCards();
    }

    @GetMapping(path = "/{id}")
    public CatCard getById(@PathVariable int id) {
        CatCard catCard = catCardDao.getCatCardById(id);
        if (catCard == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        } else {
            return catCard;
        }

    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard getRandom() {
        CatCard randomCard = new CatCard();
        CatFact catFact = catFactService.getFact();
        CatPic catPic = catPicService.getPic();
        randomCard.setCatFact(catFact.getText());
        randomCard.setImgUrl(catPic.getFile());
        return randomCard;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatCard createCard(@Valid @RequestBody CatCard catCard) {
        System.out.println(catCard.getCatCardId() + catCard.getCatFact() + catCard.getCaption() + catCard.getImgUrl());
        return catCardDao.createCatCard(catCard);
    }

    @PutMapping(path = "/{id}")
    public CatCard updateCard(@Valid @RequestBody CatCard catCard, @PathVariable int id) {
        catCard.setCatCardId(id);
        try {
            return catCardDao.updateCatCard(catCard);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auction not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id) {
        catCardDao.deleteCatCardById(id);
    }


}
