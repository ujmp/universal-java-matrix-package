# Universal Java Matrix Package
> #### A Java library for sparse and dense matrices, linear algebra, visualization and big data

## Project Website: 

https://ujmp.org

## About

The Universal Java Matrix Package (UJMP) is an open source library for dense and sparse matrix computations and linear algebra in Java. In addition to the basic operations like matrix multiplication, matrix inverse or matrix decomposition, it also supports visualization, JDBC import/export and many other useful functions such as mean, correlation, standard deviation, mutual information, or the replacement of missing values.

It's a swiss army knife for data processing in Java, tailored to machine learning applications.

##In a Nutshell:

- Dense and sparse matrices in multiple dimensions
- Matrix inverse, pseudo inverse, determinant, SVD, LU, QR, Cholesky, Eigenvalue decomposition
- Multi-threaded and lighting fast
- Handle terabyte-sized matrices on disk
- Visualize and edit as heatmap, graph, plot
- Treat every type of data as a matrix
- TXT, CSV, PNG, JPG, HTML, XLS, XLSX, PDF, LaTeX, Matlab, MDB
- Free and open source (LGPL)

## Quick Start

```
// create a dense empty matrix with 4 rows and 4 columns
Matrix dense = DenseMatrix.Factory.zeros(4, 4);

// set entry at row 2 and column 3 to the value 5.0
dense.setAsDouble(5.0, 2, 3);

// set some other values
dense.setAsDouble(1.0, 0, 0);
dense.setAsDouble(3.0, 1, 1);
dense.setAsDouble(4.0, 2, 2);
dense.setAsDouble(-2.0, 3, 3);
dense.setAsDouble(-2.0, 1, 3);

// print the final matrix on the console
System.out.println(dense);

// create a sparse empty matrix with 4 rows and 4 columns
Matrix sparse = SparseMatrix.Factory.zeros(4, 4);
sparse.setAsDouble(2.0, 0, 0);

// basic calculations
Matrix transpose = dense.transpose();
Matrix sum = dense.plus(sparse);
Matrix difference = dense.minus(sparse);
Matrix matrixProduct = dense.mtimes(sparse);
Matrix scaled = dense.times(2.0);

Matrix inverse = dense.inv();
Matrix pseudoInverse = dense.pinv();
double determinant = dense.det();

Matrix[] singularValueDecomposition = dense.svd();
Matrix[] eigenValueDecomposition = dense.eig();
Matrix[] luDecomposition = dense.lu();
Matrix[] qrDecomposition = dense.qr();
Matrix choleskyDecomposition = dense.chol();
```

## References

- Holger Arndt, Markus Bundschus, Andreas Nägele: [Towards a Next-Generation Matrix Library for Java](https://holger-arndt.de/library/COMPSAC2009-ujmp-draft.pdf), 33rd Annual IEEE International Computer Software and Applications Conference (COMPSAC), 2009
- Holger Arndt: [Universal Java Matrix Package](https://holger-arndt.de/library/MLOSS2010.pdf), Workshop on Machine Learning Open Source Software, 27th International Conference on Machine Learning (ICML), 2010


## License

The Universal Java Matrix Package is licensed under the [GNU Lesser General Public License v3.0](http://www.gnu.org/licenses/lgpl-3.0.en.html).
