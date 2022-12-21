package project1;

import java.util.Random;

public class matrixProject
{
    public static void main(String[] args)
    {
        int runs = 2048;
		int tests = 10;
		int n;
		int realN = 0;
		long startTime, endTime;
		long timeClassic = 0;
		long timeDC = 0;
		long timeStrassen = 0;
		int[][] matrix1, matrix2;
		int [][] sample1, sample2, sample3;

		for(int i = 1; i < runs; i++)
        {
			n = i;
			
			for(int j = 0; j < 1; j++)
            {
				int[][] matrix3  = new int[n][n];
				matrix1 = generateMatrix(n);
				matrix2 = generateMatrix(n); 
				
				if(isPowerOfTwo(n) == false)
                {
					matrix1 = modifyMatrix(matrix1, n);
					matrix2 = modifyMatrix(matrix2, n);
					matrix3 = modifyMatrix(matrix3, n);
				    realN = n;
				    n++;
				}
				else
                {
					realN = n;
				}
				
				startTime = System.nanoTime();
				sample1 = classic(matrix1, matrix2, matrix1.length);
				endTime = System.nanoTime();
				timeClassic += endTime - startTime;

				startTime = System.nanoTime();
				sample2 = divideAndConquer(matrix1, matrix2, matrix1.length);
				endTime = System.nanoTime();
				timeDC += endTime - startTime;
                
				startTime = System.nanoTime();
				sample3 = strassenAlgo(matrix1, matrix2, matrix3, matrix1.length);
				endTime = System.nanoTime();
				timeStrassen += endTime - startTime; 
			}

			timeClassic= timeClassic / tests;
			timeDC = timeDC / tests;
			timeStrassen = timeStrassen / tests;

			System.out.println("n = " + realN + "\nClassic Algorithm: " + timeClassic
							+ " nanoseconds\nDivide and Conquer Algorithm: " + timeDC
							+ " nanoseconds\nStrassen Algorithm: " + timeStrassen + " nanoseconds\n");
		}		
	}
	
	public static int[][] modifyMatrix(int[][] matrix, int n)
    {
		int count = 0;
		count = changePowerOfTwo(n);
	  
		int[][] newMatrix = new int[matrix.length + count][matrix[0].length + count];

	    for(int row = 0; row < newMatrix.length; row++)
        {
	        for(int col = 0; col < newMatrix[0].length; col++)
            {
	            if(row < matrix.length && col < matrix[0].length)
	            {
	                newMatrix[row][col] = matrix[row][col];
	            }
	            else
	            {
	                newMatrix[row][col] = 0;
	            }
	        }

	    }
	    return newMatrix;
	}
	
	public static boolean isPowerOfTwo(int n)
    {
	    while(n % 2 == 0)
        {
	        n = n / 2;
	    }
	 
	    if(n == 1)
        {
	        return true;
	    }
        else
        {
	        return false;
	    }
	}
	
	public static int changePowerOfTwo(int n)
    {
		if(n == 1)
        {
			return 1;
		}
        else if(n == 2)
        {
			return 0;
		}
        else if(n < 4)
        {
			return 1;
		}
        else if(n < 8)
        {
			return (8 - n);
		}
        else if(n < 16)
        {
			return (16 - n);
		}
        else if(n < 32)
        {
			return (32 - n);
		}
        else if(n < 64)
        {
			return (64 - n);
		}
        else if(n < 128)
        {
			return (128 - n);
		}
        else if(n < 256)
        {
			return (256 - n);
		}
        else if(n < 512)
        {
			return (512 - n);
		}
        else if(n < 1024)
        {
			return (1024 - n);
		}
        else if(n < 2048)
        {
			return (2048 - n);
		}
        else
        {
			return 0;
		}
	}
	
	public static int[][] generateMatrix(int n)
    {
        int[][] matrix = new int[n][n];
        int random;
		Random r = new Random();

		for(int i = 0; i < n; i++)
        {
			for(int j = 0; j < n; j++)
            {
				random = r.nextInt((100 - (-100)) + 1) + (-100);
				matrix[i][j] = random;
			}
		}
		return matrix;
	}

	public static int[][] classic(int[][] mat1, int[][] mat2, int n)
    {
		int[][] newMat = new int[n][n];

		for(int i = 0; i < n; i++)
        {
			for(int j = 0; j < n; j++)
            {
				newMat[i][j] = 0;
			}
		}

		for(int i = 0; i < n; i++)
        {
			for(int j = 0; j < n; j++)
            {
				for(int k = 0; k < n; k++)
                {
					newMat[i][j] += mat1[i][k] * mat2[k][j];
				}
			}
		}
		return newMat;
	}

    public static int[][] divideAndConquer(int[][] mat1, int[][] mat2, int n)
    {
		int[][] newMat = new int[n][n];

		if(n == 1)
        {
			newMat[0][0] = mat1[0][0] * mat2[0][0];
			return newMat;
		}
        else
        {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			divideMatrix(mat1, A11, 0, 0);
			divideMatrix(mat1, A12, 0, n / 2);
			divideMatrix(mat1, A21, n / 2, 0);
			divideMatrix(mat1, A22, n / 2, n / 2);
			divideMatrix(mat2, B11, 0, 0);
			divideMatrix(mat2, B12, 0, n / 2);
			divideMatrix(mat2, B21, n / 2, 0);
			divideMatrix(mat2, B22, n / 2, n / 2);

			int[][] C11 = addMatrix(divideAndConquer(A11, B11, n / 2),
					divideAndConquer(A12, B21, n / 2), n / 2);
			int[][] C12 = addMatrix(divideAndConquer(A11, B12, n / 2),
					divideAndConquer(A12, B22, n / 2), n / 2);
			int[][] C21 = addMatrix(divideAndConquer(A21, B11, n / 2),
					divideAndConquer(A22, B21, n / 2), n / 2);
			int[][] C22 = addMatrix(divideAndConquer(A21, B12, n / 2),
					divideAndConquer(A22, B22, n / 2), n / 2);

			mergeMatrix(C11, newMat, 0, 0);
			mergeMatrix(C12, newMat, 0, n / 2);
			mergeMatrix(C21, newMat, n / 2, 0);
			mergeMatrix(C22, newMat, n / 2, n / 2);
		}
		return newMat;
	}

    public static int[][] strassenAlgo(int[][] matrix1, int[][] matrix2, int[][] matrix3,  int n)
	{
		if(n == 1)
		{
			matrix3[0][0] = matrix1[0][0] * matrix2[0][0];
			return matrix3;
		}else if(n == 2)
		{
			matrix3[0][0] = (matrix1[0][0] * matrix2[0][0]) + (matrix1[0][1] * matrix2[1][0]);
			matrix3[0][1] = (matrix1[0][0] * matrix2[0][1]) + (matrix1[0][1] * matrix2[1][1]);
			matrix3[1][0] = (matrix1[1][0] * matrix2[0][0]) + (matrix1[1][1] * matrix2[1][0]);
			matrix3[1][1] = (matrix1[1][0] * matrix2[0][1]) + (matrix1[1][1] * matrix2[1][1]);	
		}
		else
		{
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			int[][] P = new int[n / 2][n / 2]; 
			int[][] Q = new int[n / 2][n / 2];
			int[][] R = new int[n / 2][n / 2];
			int[][] S = new int[n / 2][n / 2];
			int[][] T = new int[n / 2][n / 2];
			int[][] U = new int[n / 2][n / 2];
			int[][] V = new int[n / 2][n / 2];
			
			divideMatrix(matrix1, A11, 0, 0);
			divideMatrix(matrix1, A12, 0, n / 2);
			divideMatrix(matrix1, A21, n / 2, 0);
			divideMatrix(matrix1, A22, n / 2, n / 2);
			
			divideMatrix(matrix2, B11, 0, 0);
			divideMatrix(matrix2, B12, 0, n / 2);
			divideMatrix(matrix2, B21, n / 2, 0);
			divideMatrix(matrix2, B22, n / 2, n / 2);

			strassenAlgo(A11, subtractMatrix(B12, B22, n / 2), P, n / 2); 
			strassenAlgo(addMatrix(A11, A12, n / 2), B22, Q, n / 2);	
			strassenAlgo(addMatrix(A21, A22, n / 2), B11, R, n / 2); 
			strassenAlgo(A22, subtractMatrix(B21, B11, n / 2), S, n / 2);
			strassenAlgo(addMatrix(A11, A22, n / 2), addMatrix(B11, B22, n / 2), T, n / 2);
			strassenAlgo(subtractMatrix(A12, A22, n / 2), addMatrix(B21, B22, n / 2), U, n / 2);
			strassenAlgo(subtractMatrix(A11, A21, n / 2), addMatrix(B11, B12, n / 2), V, n / 2);

			int[][] C11 = addMatrix(subtractMatrix(addMatrix(T, U, U.length), Q, Q.length), S, S.length);
			int[][] C12 = addMatrix(P, Q, Q.length);
			int[][] C21 = addMatrix(R, S, S.length);
			int[][] C22 = addMatrix(subtractMatrix(P, R, R.length), subtractMatrix(T, V, V.length), V.length); 

			mergeMatrix(C11, matrix3, 0, 0);
			mergeMatrix(C12, matrix3, 0, n / 2);
			mergeMatrix(C21, matrix3, n / 2, 0);
			mergeMatrix(C22, matrix3, n / 2, n / 2);
		}
		return matrix3;
	}
	
	private static void divideMatrix(int[][] firstMatrix, int[][] newMatrix, int firstRow, int firstCol)
    {
		int currentCol = firstCol;

		for(int i = 0; i < newMatrix.length; i++)
        {
			for(int j = 0; j < newMatrix.length; j++)
            {
				newMatrix[i][j] = firstMatrix[firstRow][currentCol++];
			}
			currentCol = firstCol;
			firstRow++;
		}
	}
	
	private static int[][] addMatrix(int[][] mat1, int[][] mat2, int n)
    {
		int[][] newMat = new int[n][n];

		for(int i = 0; i < n; i++)
        {
			for(int j = 0; j < n; j++)
            {
				newMat[i][j] = mat1[i][j] + mat2[i][j];
			}
		}
		return newMat;
	}
	
	
	
	private static void mergeMatrix(int[][] firstMatrix, int[][] newMatrix, int firstRow, int firstCol)
    {
		int currentCol = firstCol;

		for(int i = 0; i < firstMatrix.length; i++)
        {
			for(int j = 0; j < firstMatrix.length; j++)
            {
				newMatrix[firstRow][currentCol++] = firstMatrix[i][j];
			}
			currentCol = firstCol;
			firstRow++;
		}
	}
	
	private static int[][] subtractMatrix(int[][] matrix1, int[][] matrix2, int n)
    {
		int[][] newMat = new int[n][n];

		for(int i = 0; i < n; i++)
        {
			for(int j = 0; j < n; j++)
            {
				newMat[i][j] = matrix1[i][j] - matrix2[i][j];
			}
		}
		return newMat;
	}
}
