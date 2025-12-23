package rs.fi.appl.olb.blog;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public class BlogService {
}

record Category(
        @Id Integer id,
        String name,
        String description
) {
}

record Tag(
        @Id Integer id,
        String name
) {
}

record Post(
        @Id Integer id,
        Integer categoryId,
        String title,
        String content,
        LocalDate createdAt,
        LocalDate updatedAt,
        Category category,
        List<Tag> tags
) {
}


record PostTag(
        Integer postId,
        Integer tagId
) {
}


interface PostRepository extends ListPagingAndSortingRepository<Post, Integer> { }

