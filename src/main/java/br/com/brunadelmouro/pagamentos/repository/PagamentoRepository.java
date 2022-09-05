package br.com.brunadelmouro.pagamentos.repository;

import br.com.brunadelmouro.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
