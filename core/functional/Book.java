package functional;

public record Book(int bookID,
                   String title,
                   String authors,
                   double averageRating,
                   String isbn,
                   long ibsn13,
                   String languageCode,
                   int noPages,
                   int ratingsCount,
                   int textReviewsCount,
//                    LocalDate publicationDate,
                   String publisher) {}
