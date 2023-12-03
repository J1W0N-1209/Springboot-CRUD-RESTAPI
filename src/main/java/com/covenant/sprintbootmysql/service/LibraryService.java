package com.covenant.sprintbootmysql.service;

import com.covenant.sprintbootmysql.model.*;
import com.covenant.sprintbootmysql.model.request.AuthorCreationRequest;
import com.covenant.sprintbootmysql.model.request.BookCreationRequest;
import com.covenant.sprintbootmysql.model.request.BookLendRequest;
import com.covenant.sprintbootmysql.model.request.MemberCreationRequest;
import com.covenant.sprintbootmysql.repository.AuthorRepository;
import com.covenant.sprintbootmysql.repository.BookRepository;
import com.covenant.sprintbootmysql.repository.LendRepository;
import com.covenant.sprintbootmysql.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book readBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }

    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ISBN");
    }

    public Book createBook(BookCreationRequest book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if(!author.isPresent()) {
            throw new EntityNotFoundException("Author Not Found");
        }

        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book,bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request,member);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember(Long id,MemberCreationRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(!optionalMember.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = optionalMember.get();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());

        return memberRepository.save(member);
    }

    public Author createAuthor(AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request,author);
        return authorRepository.save(author);
    }

    public List<String> lendABook(BookLendRequest request) {
        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        if(!memberForId.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }

        List<String> booksApprovedToBurrow = new ArrayList<>();

        request.getBookIds().forEach(bookId -> {
            Optional<Book> bookForId = bookRepository.findById(bookId);
            if(!bookForId.isPresent()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }

            Optional<Lend> burrowedBook = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);
            if(!bookForId.isPresent()) {
                booksApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }
        });
        return booksApprovedToBurrow;
    }

    public List<Author> readAuthors() {
        return authorRepository.findAll();
    }

    public Book updateBook(Long bookId,BookCreationRequest request) {
        Optional<Author> author = authorRepository.findById(request.getAuthorId());
        if(!author.isPresent()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()) {
            throw new EntityNotFoundException("Book Not Found");
        }
        Book book = optionalBook.get();
        book.setIsbn(request.getIsbn());
        book.setName(request.getName());
        book.setAuthor(author.get());
        return bookRepository.save(book);
    }

    public List<Member> readMembers() {
        return memberRepository.findAll();
    }
}
