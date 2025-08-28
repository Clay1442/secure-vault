package com.securevault.secure_vault_api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.securevault.secure_vault_api.security.CryptoConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "note_tb")
public class Note implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	private String username;
    
	@Column(nullable = false)
    @Convert(converter = CryptoConverter.class)
	private String password;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updateAt;
    
	@Column(columnDefinition = "TEXT")
	private String description;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
    private User user;

	public Note() {
		super();
	}

	public Note(Long id, String title, User user, LocalDateTime createdAt, LocalDateTime updateAt, String description,
			String password, String username) {
		super();
		this.id = id;
		this.title = title;
		this.user = user;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.description = description;
		this.password = password;
		this.username = username;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
