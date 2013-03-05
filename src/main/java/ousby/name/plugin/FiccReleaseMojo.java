package ousby.name.plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Apply a ficc release label to a single file. 
 * 
 * Intended use is to apply a label to 'tpackage' file.
 *
 */
@Mojo( name = "ficc-release", defaultPhase = LifecyclePhase.PACKAGE )
public class FiccReleaseMojo extends AbstractMojo
{
    /**
     * Output directory
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private String outputDirectory;    

    /**
     * The intended deployment directory where ficc release will install the file.
     */
    @Parameter(property = "targetReleaseDir", required = true )
    private String targetReleaseDir;

    /**
     * The file to be released.
     */
    @Parameter(defaultValue = "${project.build.directory}/${project.artifactId}.tpackage", property = "releaseFile", required = true )
    private String releaseFile;

    /**
     * The ficc release script responsible creating and applying the label
     */
    @Parameter(defaultValue = "/some/script", property = "ficcReleaseScript", required = true )
    private String releaseScript;
    

    public void execute()
        throws MojoExecutionException
    {
        // Check required files exist
        Path releaseFile = Paths.get( this.releaseFile );
        Path releaseScript = Paths.get( this.releaseScript );
        checkFileExists( releaseFile );
      //  checkFileExists( releaseScript );
        
        // Create working directories
        Path pluginOutputDir = Paths.get( outputDirectory, "ficc-release" ); 
        Path releaseDir = Paths.get( pluginOutputDir.toString(), targetReleaseDir );
        createDirectory( pluginOutputDir );
        createDirectory( releaseDir );
       
        // Prepare release
        copyReleaseFileToReleaseDirectory( releaseFile, releaseDir );
        
        // Apply label to release file
        String label = createLabel( releaseScript );
        applyLabel( releaseScript, label, pluginOutputDir );
    }
    
    
    private void checkFileExists( Path file ) throws MojoExecutionException
    {
        if ( !Files.exists( file ) )        
            throw new MojoExecutionException( "Required file cannot be found:  " + file );        
    }

    private void createDirectory(Path directory) 
        throws MojoExecutionException 
    {        
        try 
        {
            Files.createDirectories( directory );
            getLog().info( String.format( "Directory %s created.", directory.toString() ) );
        } 
        catch (IOException e) 
        {
            throw new MojoExecutionException( "Error creating dir: " + directory.toString(), e );
        }
    }

    private void copyReleaseFileToReleaseDirectory( Path releaseFile, Path releaseDir ) 
        throws MojoExecutionException 
    {        
        try 
        {
            Files.copy( releaseFile, Paths.get( releaseDir.toString(), releaseFile.getFileName().toString() ) );
            getLog().info( String.format( "Release file %s copied to %s.", releaseFile.getFileName().toString(), releaseDir.toString() ) );
        } 
        catch (IOException e) 
        {
            throw new MojoExecutionException( "Error copying release file to reelase dir", e );
        }
    }   

    private String createLabel( Path releaseScript )
    {
        // run create label script
//        Path output = Files.createTempFile("label", "txt");
//        
//        ProcessBuilder script = new ProcessBuilder( releaseScript.toString() );
//        script.redirectErrorStream(true);        
//        script.redirectOutput(Redirect.appendTo(output.toFile()));
//        
//        Process process = script.start();
//        process.waitFor();
//        int exitCode = process.exitValue();
        
        // apply label to all files below pwd
        
        // capture label output / content and dump to file
        return "";
    }

    private void applyLabel( Path releaseScript, String label, Path pluginOutputDir ) 
    {
        // create label
        
        // apply label to all files below pwd
        
        // capture label output / content and dump to file
        
    }
    
    
    


//  File touch = new File( f, "touch.txt" );
//
//  FileWriter w = null;
//  try
//  {
//      w = new FileWriter( touch );
//
//      w.write( "touch.txt" );
//  }
//  catch ( IOException e )
//  {
//      throw new MojoExecutionException( "Error creating file " + touch, e );
//  }
//  finally
//  {
//      if ( w != null )
//      {
//          try
//          {
//              w.close();
//          }
//          catch ( IOException e )
//          {
//              // ignore
//          }
//      }
//  }
}
