package pentacode.backend.code.common.service;

import pentacode.backend.code.common.dto.TicketDTO;
import pentacode.backend.code.common.entity.Ticket;
import pentacode.backend.code.common.repository.TicketRepository;
import pentacode.backend.code.common.service.base.BaseService;

public class TicketService extends BaseService<Ticket> {
    TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository) {
        super(ticketRepository);
        this.ticketRepository = ticketRepository;
    }
    // public TicketDTO createTicket(TicketDTO ticketDTO) {
    //     Ticket ticket = new Ticket();
    //     ticket.setSubject(ticketDTO.getSubject());
    //     ticket.setTicketRequests(ticketDTO.getTicketRequests());
    //     ticket.setTicketResponses(ticketDTO.getTicketResponses());
    //     ticket.setResolved(ticketDTO.isResolved());
    //     ticket.setUser(ticketDTO.getUser());
    //     return new TicketDTO(ticketRepository.save(ticket));
    // }
    
    
    
}
