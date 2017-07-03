package shop.main.data.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.ParcelSize;

public interface ParcelSizeDAO extends JpaRepository<ParcelSize, Long> {

}
