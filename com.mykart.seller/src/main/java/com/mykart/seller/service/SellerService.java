package com.mykart.seller.service;

import com.myKart.infra.exception.BussinessException;
import com.mykart.seller.dto.Seller;
import com.mykart.seller.repository.SellerRepository;
import com.mykart.seller.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SellerService {
	
	@Autowired
	private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    // --- Registration ---
	public String addSeller(Seller newSeller) throws Exception {
		try {
			Seller existingSeller = sellerRepository.findBySellerEmail(newSeller.getSellerEmail());
			if (!Objects.isNull(existingSeller)) {
				throw new BussinessException("Seller Account with current email already present");
			}
            
            // Encrypt Password
            newSeller.setSellerPassword(passwordEncoder.encode(newSeller.getSellerPassword()));
			sellerRepository.save(newSeller);
            return "Seller Added Successfully";
		} catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
			throw new Exception("Error while adding seller: " + e.getMessage());
		}
	}

    // --- Login ---
    public String login(String email, String password) throws Exception {
        try {
            Seller seller = sellerRepository.findBySellerEmail(email);
            if (seller == null) {
                throw new BussinessException("Seller Not Found");
            }
            
            if (!passwordEncoder.matches(password, seller.getSellerPassword())) {
                throw new BussinessException("Invalid email or password");
            }
            // Generate Token
            return jwtUtil.generateToken(seller);

        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("An error occurred during login: " + e.getMessage());
        }
    }
	
    // --- Read ---
	public Seller getSeller(String id) throws Exception {
		try {
			Seller seller = sellerRepository.findBySellerId(id);
            if (seller == null) {
                throw new BussinessException("Seller not found with ID: " + id);
            }
            return seller;
		} catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
			throw new Exception("Error while fetching seller: " + e.getMessage());
		}
	}

    public List<Seller> getAllSellers() throws Exception {
        try {
            List<Seller> sellers = sellerRepository.findAll();
            if (sellers.isEmpty()) {
                throw new BussinessException("No sellers found.");
            }
            return sellers;
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error fetching sellers: " + e.getMessage());
        }
    }

    // --- Update (Generic Field Update) ---
    @Transactional
    public String updateSellerDetails(String sellerId, String updateType, String newValue) throws Exception {
        try {
            if(!sellerRepository.existsById(sellerId)) {
                throw new BussinessException("Seller not found");
            }

            if(updateType.equals("password")) {
                // Special handling for password to encrypt it
                String encryptedPass = passwordEncoder.encode(newValue);
                updateSellerField(sellerId, "sellerPassword", encryptedPass);
            } else if (updateType.equals("email")) {
                updateSellerField(sellerId, "sellerEmail", newValue);
            } else if (updateType.equals("phoneNo")) {
                updateSellerField(sellerId, "sellerPhoneNo", newValue);
            } else if (updateType.equals("name")) {
                updateSellerField(sellerId, "sellerName", newValue);
            } else {
                throw new BussinessException("Invalid update type provided");
            }
            
            return "Seller " + updateType + " Updated Successfully";

        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error updating seller: " + e.getMessage());
        }
    }

    // --- Delete ---
    @Transactional
    public String deleteSeller(String sellerId) throws Exception {
        try {
            if (!sellerRepository.existsById(sellerId)) {
                throw new BussinessException("Seller not found.");
            }
            sellerRepository.deleteById(sellerId);
            return "Seller Deleted Successfully";
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while deleting seller: " + e.getMessage());
        }
    }

    private void updateSellerField(String id, String fieldName, Object newValue) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set(fieldName, newValue);
        mongoTemplate.updateFirst(query, update, Seller.class);
    }
}