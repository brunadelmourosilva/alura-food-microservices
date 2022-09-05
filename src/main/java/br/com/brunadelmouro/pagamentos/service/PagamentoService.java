package br.com.brunadelmouro.pagamentos.service;

import br.com.brunadelmouro.pagamentos.dto.PagamentoDto;
import br.com.brunadelmouro.pagamentos.model.Pagamento;
import br.com.brunadelmouro.pagamentos.model.Status;
import br.com.brunadelmouro.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;
    private ModelMapper modelMapper;

    public PagamentoService(PagamentoRepository pagamentoRepository, ModelMapper modelMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.modelMapper = modelMapper;
    }

    public Page<PagamentoDto> findAllPayments(Pageable pageable) {
        return pagamentoRepository
                .findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDto.class));
    }

    public PagamentoDto findPaymentById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto createPayment(PagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class); //convert dto to entity class
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto updatePayment(Long id, PagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setId(id); //insert id on dto converter
        pagamento = pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void deletePayment(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
