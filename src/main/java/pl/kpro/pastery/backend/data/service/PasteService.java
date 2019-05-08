package pl.kpro.pastery.backend.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kpro.pastery.backend.data.repositories.PasteRepository;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class PasteService
{
    @Autowired
    private PasteRepository pasteRepository;



}
