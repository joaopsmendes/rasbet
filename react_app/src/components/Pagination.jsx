import React from 'react';
import Pagination from 'react-paginate';
import { useState } from 'react';

const PaginationComp = (props) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage] = useState(10);
  const [pageCount, setPageCount] = useState(Math.ceil(props.items.length / itemsPerPage));

  const handlePageClick = (data) => {
    setCurrentPage(data.selected);
  };

  const paginatedItems = props.items.slice(
    currentPage * itemsPerPage,
    (currentPage + 1) * itemsPerPage
  );

  return (
    <div>
      {paginatedItems.map((item) => (
        <div key={item.id}>{item.name}</div>
      ))}
      <Pagination
        pageCount={pageCount}
        onPageChange={handlePageClick}
      />
    </div>
  );
};

export default PaginationComp;