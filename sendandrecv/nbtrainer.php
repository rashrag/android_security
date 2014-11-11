<?php
//set_time_limit(0);
$inputlines=file_get_contents("test.txt");
#echo $inputlines;
$prior=array('B'=>0,'M'=>0);
$sentdict=array('B'=>array(),'M'=>array());

$permissionset=array(); #cal array_unique
$appname=array();
$inputlines=explode("\n",$inputlines);
foreach($inputlines as $k)
{
		$k=trim($k);
		$spl1=explode(":",$k); #split appname and permissions
		$spl2=explode(";",$spl1[1]); #split permissions and classification
		array_push($appname,$spl1[0]);
		$prior[$spl2[1]]=$prior[$spl2[1]]+1;
		$permlist=explode(",",$spl2[0]);
		if(strlen($spl2[0]) != 2 )
		{
		foreach($permlist as $v)
		{
			$v=trim($v);
			array_push($sentdict[$spl2[1]],$v);
			array_push($permissionset,$v);
		}
		}
		$permissionset1=array_unique($permissionset);

}
echo "basics created";
echo count($prior).",".count($inputlines)."<br>";

foreach($prior as $k=>$v)
{
	$prior[$k]=$prior[$k]/count($inputlines);
}
echo "prior done";
$likelihoodb=array();
$likelihoodm=array();
$i=0;
foreach($permissionset1 as $k=>$v)
{
	$likelihoodb[$permissionset1[$k]]=0;
	$likelihoodm[$permissionset1[$k]]=0;
	$i=$i+1;
}
echo "likeli done";
echo "<br>";
foreach($likelihoodb as $k=>$v)
{
		$fullct1=array_count_values($sentdict['B']);
		$fullct2=array_count_values($sentdict['M']);	
		if(count($sentdict['B']))
		{
			if(array_key_exists($k,$fullct1))
				$likelihoodb[$k]=($fullct1[$k]+1)/count($sentdict['B']);
			else
				$likelihoodb[$k]=1/count($sentdict['B']);
			
		}
}
foreach($likelihoodm as $k=>$v)
{
			$fullct2=array_count_values($sentdict['M']);	
		if(count($sentdict['M']))
		{
			if(array_key_exists($k,$fullct2))
				$likelihoodm[$k]=($fullct2[$k]+1)/count($sentdict['M']);
			else
				$likelihoodm[$k]=1/count($sentdict['M']);
		}
}
extract($_GET);
//INPUT: appname and permissions
$input2=$permissions;
$input2=explode(",",$input2);
		$ben=1;
		$mal=1;
		foreach($input2 as $k)
		{
			$k=trim($k);
			if(array_key_exists($k,$likelihoodb))
				$ben=$ben*$likelihoodb[$k];
			else
			{
				$ben=$ben/count($sentdict['B']);
			}
			if(array_key_exists($k,$likelihoodm))
				$mal=$mal*$likelihoodm[$k];
			else
			{
				$mal=$mal/count($sentdict['M']);
			}
			
		
		}

		$ben=$ben*$prior['B'];
		$mal=$mal*$prior['M'];
	
		
		if($ben>=$mal)
			echo "$appname is more benign than malicious,0";
		else
			echo "$appname is more malicious than benign,1";
	
?>

   